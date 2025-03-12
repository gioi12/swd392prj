<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Basic metadata -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title> Đăng kí</title>
        <link rel="icon" href="images/logo/logo_icon.png" type="image/x-icon">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }
            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            .login_form {
                width: 100%;
                max-width: 400px;
                padding: 30px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                margin-bottom: 30px;
                font-size: 24px;
                font-weight: 600;
                color: #333;
            }
            .field {
                margin-bottom: 20px;
            }
            .label_field {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
                color: #333;
            }
            .styled-input {
                width: 100%;
                padding: 12px;
                font-size: 16px;
                border: 2px solid #ccc;
                border-radius: 5px;
                outline: none;
                transition: border-color 0.3s ease;
            }
            .styled-input:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            }
            .btn-primary {
                width: 100%;
                padding: 12px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .forgot {
                display: block;
                text-align: center;
                color: #007bff;
                font-size: 14px;
                margin-top: 15px;
                text-decoration: none;
            }
            .forgot:hover {
                text-decoration: underline;
            }
            .error-message {
                color: orangered;
                margin-left: 150px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="login_form">
                <h2>Đăng kí tài khoản</h2>
                <form action="userregisterservlet" method="post">
                    <div class="field">
                        <label class="label_field">Tài khoản</label>
                        <input type="text" name="username" placeholder="Nhập tài khoản" required class="styled-input"/>
                        <div class="error-message">${usernameError}${usernameExist}</div>
                    </div>

                     <div class="field">
                        <label class="label_field">Email</label>
                        <input type="email" name="email" placeholder="Nhập email" required class="styled-input"
                               value="${sessionScope.email != null ? sessionScope.email : ''}" />
                        <div class="error-message">${emailExist}${invalidEmail}</div>
                    </div>

                    <div class="field">
                        <label class="label_field">Mật khẩu</label>
                        <input type="password" name="password" placeholder="Nhập mật khẩu" required class="styled-input"/>
                        <div class="error-message">${passwordError}</div>
                    </div>

                    <div class="field">
                        <label class="label_field">Họ</label>
                        <input type="text" name="firstName" placeholder="Nhập họ" required class="styled-input"/>
                    </div>

                    <div class="field">
                        <label class="label_field">Tên</label>
                        <input type="text" name="lastName" placeholder="Nhập tên" required class="styled-input"/>
                    </div>

                    <div class="field">
                        <label class="label_field">Số điện thoại</label>
                        <input type="text" name="phone" placeholder="Nhập số điện thoại" required class="styled-input"/>
                    </div>

                    <div class="field">
                        <button type="submit" class="btn-primary">Đăng kí</button>
                    </div>

                    <div class="field">
                        <a class="forgot" href="login.jsp">Đã có tài khoản? Đăng nhập</a>
                    </div>
                </form>

            </div>
        </div>
    </body>
</html>
