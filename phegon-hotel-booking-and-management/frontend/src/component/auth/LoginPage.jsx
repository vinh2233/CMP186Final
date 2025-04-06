// // import React, { useState } from "react";
// // import { useNavigate, useLocation } from "react-router-dom";
// // import ApiService from "../../service/ApiService";

// // function LoginPage() {
// //   const [email, setEmail] = useState("");
// //   const [password, setPassword] = useState("");
// //   const [error, setError] = useState("");
// //   const navigate = useNavigate();
// //   const location = useLocation();

// //   const from = location.state?.from?.pathname || "/home";

// //   const handleSubmit = async (e) => {
// //     e.preventDefault();

// //     if (!email || !password) {
// //       setError("Please fill in all fields.");
// //       setTimeout(() => setError(""), 5000);
// //       return;
// //     }

// //     try {
// //       const response = await ApiService.loginUser({ email, password });
// //       if (response.statusCode === 200) {
// //         localStorage.setItem("token", response.token);
// //         localStorage.setItem("role", response.role);
// //         navigate(from, { replace: true });
// //       }
// //     } catch (error) {
// //       setError(error.response?.data?.message || error.message);
// //       setTimeout(() => setError(""), 5000);
// //     }
// //   };

// //   return (
// //     <div className="auth-container">
// //       <h2>Login</h2>
// //       {error && <p className="error-message">{error}</p>}
// //       <form onSubmit={handleSubmit}>
// //         <div className="form-group">
// //           <label>Email: </label>
// //           <input
// //             type="email"
// //             value={email}
// //             onChange={(e) => setEmail(e.target.value)}
// //             required
// //           />
// //         </div>
// //         <div className="form-group">
// //           <label>Password: </label>
// //           <input
// //             type="password"
// //             value={password}
// //             onChange={(e) => setPassword(e.target.value)}
// //             required
// //           />
// //         </div>
// //         <button type="submit">Login</button>
// //       </form>

// //       <p className="register-link">
// //         Don't have an account? <a href="/register">Register</a>
// //       </p>
// //     </div>
// //   );
// // }

// // export default LoginPage;
// import React, { useState } from "react";
// import { useNavigate, useLocation } from "react-router-dom";
// import ApiService from "../../service/ApiService";
// import "./LoginPage.css"; // Sử dụng file CSS đã cập nhật

// function LoginPage() {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");
//   const [error, setError] = useState("");
//   const navigate = useNavigate();
//   const location = useLocation();

//   const from = location.state?.from?.pathname || "/home";

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!email || !password) {
//       setError("Please fill in all fields.");
//       setTimeout(() => setError(""), 5000);
//       return;
//     }

//     try {
//       const response = await ApiService.loginUser({ email, password });
//       if (response.statusCode === 200) {
//         localStorage.setItem("token", response.token);
//         localStorage.setItem("role", response.role);
//         navigate(from, { replace: true });
//       }
//     } catch (error) {
//       setError(error.response?.data?.message || error.message);
//       setTimeout(() => setError(""), 5000);
//     }
//   };

//   return (
//     <div className="auth-page">
//       <div className="background-video-container">
//         <video autoPlay loop muted className="background-video">
//           <source src="/path-to-your-video.mp4" type="video/mp4" />
//         </video>
//       </div>

//       <div className="auth-container">
//         <h2 className="login-title">Login</h2>
//         {error && <p className="error-message">{error}</p>}
//         <form onSubmit={handleSubmit} className="login-form">
//           <div className="form-group">
//             <label>Email: </label>
//             <input
//               type="email"
//               value={email}
//               onChange={(e) => setEmail(e.target.value)}
//               required
//               className="animated-input"
//             />
//           </div>
//           <div className="form-group">
//             <label>Password: </label>
//             <input
//               type="password"
//               value={password}
//               onChange={(e) => setPassword(e.target.value)}
//               required
//               className="animated-input"
//             />
//           </div>
//           <button type="submit" className="animated-button">
//             Login
//           </button>
//         </form>

//         <p className="register-link">
//           Don't have an account? <a href="/register">Register</a>
//         </p>
//       </div>
//     </div>
//   );
// }

// export default LoginPage;
import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import ApiService from "../../service/ApiService";
import { GoogleLogin } from "@react-oauth/google"; // Import from @react-oauth/google
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const location = useLocation();

  const from = location.state?.from?.pathname || "/home";

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError("Please fill in all fields.");
      setTimeout(() => setError(""), 5000);
      return;
    }

    try {
      const response = await ApiService.loginUser({ email, password });
      if (response.statusCode === 200) {
        localStorage.setItem("token", response.token);
        localStorage.setItem("role", response.role);
        navigate(from, { replace: true });
      }
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      setTimeout(() => setError(""), 5000);
    }
  };

  const handleGoogleLogin = async (response) => {
    const googleToken = response.credential;

    try {
      const res = await ApiService.loginWithGoogle({ token: googleToken });
      if (res.statusCode === 200) {
        localStorage.setItem("token", res.token);
        localStorage.setItem("role", res.role);
        navigate(from, { replace: true });
      }
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      setTimeout(() => setError(""), 5000);
    }
  };

  return (
    <div className="auth-page">
      <div className="background-video-container">
        <video autoPlay loop muted className="background-video">
          <source src="/path-to-your-video.mp4" type="video/mp4" />
        </video>
      </div>

      <div className="auth-container">
        <h2 className="login-title">Login</h2>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label>Email: </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="animated-input"
            />
          </div>
          <div className="form-group">
            <label>Password: </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="animated-input"
            />
          </div>
          <button type="submit" className="animated-button">
            Login
          </button>
        </form>

        <div className="google-login-container">
          <GoogleLogin
            onSuccess={handleGoogleLogin}
            onError={(error) => {
              setError("Google login failed");
              setTimeout(() => setError(""), 5000);
            }}
          />
        </div>

        <p className="register-link">
          Don't have an account? <a href="/register">Register</a>
        </p>
      </div>
    </div>
  );
}

export default LoginPage;
