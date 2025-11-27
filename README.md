<h1 align="center">Resume Builder</h1>

<p align="center">
  <b>Create professional resumes within minutes.</b>
</p>

<p align="center">
  <i>Resume generator with secure authentication, real-time preview, and downloadable PDF output.</i>
</p>

---

## ⭐️ Key Features

- 🔐 **Authentication & User Management**: 
  - Secure login and signup with JWT-based authentication
  - Email verification using Brevo
  - Role-based access and protected routes

- 📄 **Resume Generation**: 
  - Real-time resume preview
  - Pre-built professional templates
  - Customizable sections:
    - Contact details
    - Experience
    - Education
    - Skills
    - Projects
    - Certifications
    - Languages
    - Interests
  - Export as PDF
 
- 💳 **Payments (Premium Features)**:
  - Razorpay integration
  - Allows unlocking premium templates

---

## ⚒️ Technical Architecture

### Frontend (React + TypeScript)
- React + Vite
- Tailwind CSS for styling
- Axios for API requests
- React Router for navigation
- Lucide REact for icons
- PDF rendering with client-side preview

### Backend
- **REST API** with Controller / Service / Repository architecture
- **MongoDB** for storing user profiles & resume data
- **Spring Security** with JWT
- **Brevo** for email verification
- **Razorpay** integration for payments
- **Cloudinary** for serving user profile images
- Global exception handling

### Database
- **MongoDB**
  - Users
  - Resume documents
  - Payment status
  - Template metadata

---

## 📥 Installation

**Backend Setup**

```bash
git clone https://github.com/schae4999/Resume-Builder.git
./mvnw clean install
```

Create an application.properties:
```ini
SPRING_DATA_MONGODB_URI=your_mongo_uri
JWT_SECRET=your_secret
BREVO_API_KEY=your_key
RAZORPAY_KEY_ID=your_id
RAZORPAY_KEY_SECRET=your_secret
```

Run backend:
```bash
./mvnw spring-boot:run
```

**Frontend Setup**

```bash
cd client
npm install
npm run dev
```

---

## 🎯 Usage Guide

1. Sign up using email verification
2. Log in to dashboard
3. Enter your resume details
4. Choose a template
5. Preview resume in real time
6. Export as PDF

---

## Credentials

Frontend UI based on a licensed template purchased from <i>Engineer Talks with Buschan</i>. Customized various UI elements and wording.
