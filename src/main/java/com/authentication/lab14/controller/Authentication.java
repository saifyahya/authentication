package com.authentication.lab14.controller;

import com.authentication.lab14.models.SiteUser;
import com.authentication.lab14.repositories.SiteUserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Authentication {
SiteUserRepo userRepo;
@Autowired
public  Authentication(SiteUserRepo userRepo) {
   this.userRepo=userRepo;
}

    @GetMapping("/login")
    public String getLoginPage(){
        return "/login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "/signup.html";
    }
    @PostMapping("/signup")
    public RedirectView signUp(String email, String userName, String password){
        SiteUser checkEmail = userRepo.findByemail(email);
        if(checkEmail!=null){
            System.out.println("email already used");
            return new RedirectView("/signup");
        }

        String hashedPassword= BCrypt.hashpw(password, BCrypt.gensalt(12));
        SiteUser siteUser = new SiteUser(userName,hashedPassword,email);
        userRepo.save(siteUser);
        return new RedirectView("login");
    }

    @PostMapping("/login")
    public RedirectView logIn(String userName, String password){
        SiteUser userFromDb =userRepo.findByuserName(userName);
        System.out.println(userName+"  "+password);

        if((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {

            return new RedirectView("/login");
        }

        return new RedirectView("/");
    }

    @PostMapping("/loginWithSecret")
    public RedirectView logInUSerWithSecret(HttpServletRequest request, String userName, String password){
        SiteUser userFromDb=userRepo.findByuserName(userName);
        System.out.println(userName+"  "+password);
        if((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {

            return new RedirectView("/loginWithSecret");
        }
        HttpSession session= request.getSession();
        session.setAttribute("userName", userName);
      session.setAttribute("userId",userFromDb.getId());

        return new RedirectView("/secretHome/"+userFromDb.getId());
    }

    @PostMapping("logoutWithSecret")
    public RedirectView logOutUserWithSecret(HttpServletRequest request){

        HttpSession session= request.getSession();
        session.invalidate();

        return new RedirectView("/loginWithSecret");
    }

    @GetMapping("/loginWithSecret")
    public String getLoginPageWithSecret(){
        return "loginWithSecret.html";
    }
    @GetMapping("/secretHome/{id}")
    public String getHomePageWithSecret(HttpServletRequest request, Model m, @PathVariable Long id){

        HttpSession session = request.getSession();
        String userName= session.getAttribute("userName").toString();
       String stringUserId= session.getAttribute("userId").toString();
        Long userId= Long.parseLong(stringUserId);
        SiteUser user = userRepo.findById(id).get();
        System.out.println("user id"+user.getId());
        if (user == null) {
            return "redirect:/loginWithSecret";
        }
        m.addAttribute("userName", userName);
        m.addAttribute("userId", userId);
        m.addAttribute("user",user);


        return "indexWithSecret.html";
    }
}

