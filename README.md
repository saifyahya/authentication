## My Space 
### Authentication App using Http Sessions and Bcrypt. It allows users to create account and login, create posts in their home page and see other users posts.


#### Authentication Controller

| Method                                      | HTTP Request | Description                                       |
|---------------------------------------------|--------------|---------------------------------------------------|
| `getSignupPage()`                           | GET          | Renders the sign-up page for new users.           |
| `signUp(String email, String userName, String password)` | POST       | Handles user sign-up. Checks for duplicate email, hashes the password, and saves the user. Redirects to login page on success or back to sign-up page on failure. |
| `logInUSerWithSecret(HttpServletRequest request, String userName, String password)` | POST | Handles user login. Checks username and password, creates a session on success, and redirects to the user's secret home page. Redirects to login page on failure. |
| `logOutUserWithSecret(HttpServletRequest request)` | POST | Handles user logout. Invalidates the session and redirects to the login page. |
| `getLoginPageWithSecret()`                  | GET          | Renders the login page for users to log in.       |
| `getHomePageWithSecret(HttpServletRequest request, Model m, @PathVariable Long id)` | GET | Renders the user's secret home page. Validates the session, compares usernames, and displays user-specific content or redirects to the login page. |

#### Post Controller

| Method                                      | HTTP Request | Description                                       |
|---------------------------------------------|--------------|---------------------------------------------------|
| `addPost(String content, Long userId)`       | POST         | Handles the addition of a new post. Retrieves the user from the database by ID, creates a new post, and saves it. Redirects to the user's secret home page. |


#### Pages

| Template Name        | Description                                                                                      |
|----------------------|--------------------------------------------------------------------------------------------------|
| General Home Page    | This is the general home page of the application. It displays the application title ("MySpace App") and an image. Users can navigate to the login and sign-up pages from here. |
| User Home Page       | This page is displayed after a user logs in. It shows the user's username, a list of all users, the ability to create new posts (if allowed), and a list of the user's posts. Users can navigate to their own home page, log out, and view other users' home pages. |
| Login Page           | The login page where users can enter their username and password to log in. After successful login, users are redirected to their user home page. |
| Sign Up Page         | The sign-up page where new users can provide their email, username, and password to create an account. After signing up, users are redirected to the login page. |


