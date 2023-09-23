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
import java.util.List;

@Controller
public class Authentication {
SiteUserRepo userRepo;
@Autowired
public  Authentication(SiteUserRepo userRepo) {
   this.userRepo=userRepo;
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


    @PostMapping("/loginWithSecret")
    public RedirectView logInUSerWithSecret(HttpServletRequest request, String userName, String password){
        SiteUser userFromDb=userRepo.findByuserName(userName);
        System.out.println(userName+"  "+password);
        if((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {
            System.out.println("incorrect password or username");
            return new RedirectView("/loginWithSecret");
        }
        HttpSession session= request.getSession();
        session.setAttribute("userName", userName);
      //session.setAttribute("userId",userFromDb.getId());

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

       if( session.getAttribute("userName")==null){  // not logged-in users will redirect to login page
           return "redirect:/loginWithSecret";
       }
        String userName= session.getAttribute("userName").toString();
        SiteUser user = userRepo.findById(id).get();
        System.out.println("username from session:"+userName);
        System.out.println("username from url:"+user.getUserName());
        if(userName.equals(user.getUserName())) {// compare username for the logged-in user with the username for the wanted home page
            m.addAttribute("canPost",true);
        }

        System.out.println("user id: "+id);
        if (user == null) {
            return "redirect:/loginWithSecret";
        }
        m.addAttribute("userName", user.getUserName());
       // m.addAttribute("userId", id);
        m.addAttribute("user",user);
        List<SiteUser> allUsers= userRepo.findAll();  // return all users
        m.addAttribute("allUsers",allUsers);

        return "indexWithSecret.html";
    }

}

