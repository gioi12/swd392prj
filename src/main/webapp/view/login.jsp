<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Online Shopping System - Login</title>
    <style>
        /* Tổng thể */
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to right, #d9f2ff, #b3e0ff);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Container của form */
        .login-container {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
            animation: fadeIn 0.8s ease-in-out;
        }

        /* Hiệu ứng xuất hiện */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Tiêu đề */
        .login-container h2 {
            margin-bottom: 20px;
            color: #005a99;
        }

        /* Ô nhập liệu */
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: bold;
            color: #004d80;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #a3cde6;
            border-radius: 5px;
            font-size: 14px;
            transition: all 0.3s ease-in-out;
        }

        input:focus {
            border-color: #0099cc;
            box-shadow: 0 0 5px rgba(0, 153, 204, 0.5);
            outline: none;
        }

        /* Nút đăng nhập */
        button {
            width: 100%;
            padding: 12px;
            background: #0099cc;
            border: none;
            color: white;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s ease-in-out;
        }

        button:hover {
            background: #0077aa;
        }

        /* Đường dẫn đăng ký */
        .register-link {
            margin-top: 15px;
            font-size: 14px;
        }

        .register-link a {
            color: #0077aa;
            text-decoration: none;
            font-weight: bold;
        }

        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Online Shopping System</h2>
    <form action="login" method="post">
        <label>Username:</label>
        <input type="text" name="user" required />

        <label>Password:</label>
        <input type="password" name="pass" required />

        <button type="submit">Login</button>
    </form>
    <div class="register-link">
        Don't have an account? <a href="register.jsp">Sign up</a>
    </div>
</div>
</body>
</html>
