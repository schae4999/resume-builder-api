## Section - 01: Register Feature with Email Verification

**Step 1:** Setup the backend project, create Spring boot project (https://start.spring.io) - Web, Mongodb, Lombok, Validation

**Step 2:** Configure the mongodb database

**Step 3:** Create application health check API endpoint

**Step 4:** Create Register API `/api/auth/register`

- Create Request and Response objects
- Create Document object to map the mongodb collection
- Create Mongodb repository
- Create Service
- Create Controller
- Test the API endpoint

**Step 5:** Send verification email to registered email address

- Add the spring email dependency
- Create an account in Brevo and the SMTP server details
- Add the SMTP details into application. properties file
- Create email service to send emails
- Update the AuthService to call email service method

**Step 6:** Verify the token

- Create a new API endpoint `/api/auth/verify-email`
- Create a finder method in UserRepository
- Create a service method to verify the token
- Update the property isEmailVerified to true

## Section - 02: Upload Profile Image

**Step 1:** Add the cloudinary dependency

**Step 2:** Add the cloudinary configuration

**Step 3:** Create FileUploadService

**Step 4:** Create API endpoint to `/api/auth/upload-image` upload profile image

**Step 5:** Test the endpoint

## Section - 03: Login Feature

**Step 1:** Add the spring security dependency

**Step 2:** Configure the Security filter chain

**Step 3:** Encode the password while registering new account

**Step 4:** Create Login request

**Step 5:** Create API endpoint `/api/auth/login` for login

**Step 6:** Create custom filter to validate JWT token

**Step 7:** Register the filter

**Step 8:** Create JwtAuthenticationEntryPoint to handle the Authentication exception