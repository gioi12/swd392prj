<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title> Xác nhận Email</title>
        <meta name="keywords" content="">
        <meta name="description" content="">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css" />

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f7fc;
                margin: 0;
                padding: 0;
            }

            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
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

            .field .form-check-label {
                font-size: 14px;
            }

            .field .form-check-input {
                margin-top: 2px;
            }

            .error-message {
                color: orangered;
                margin-left: 150px;
                font-size: 14px;
            }

            .countdown-container {
                margin-top: 10px;
                font-size: 20px;
                font-weight: bold;
                color: #007BFF;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="login_form">
                <h2>Vui lòng xác thực OTP</h2>

                <form action="verifyuserregisterservlet" method="post">
                    <div class="field">
                        <h5 style="text-align: center;">Vui lòng nhập mã số chúng tôi đã gửi cho bạn qua email ${email}.
                            <br/> Mã xác thực có giá trị trong 60s
                        </h5>
                        <div class="countdown-container">
                            <span id="countdown">60</span>
                        </div>
                    </div>

                    <div class="field">
                        <input type="text" name="otp" class="styled-input" placeholder="Nhập mã OTP" required/>
                        <input type="hidden" name="email" value="${requestScope.email}"/>
                        <input type="hidden" name="username" value="${requestScope.username}"/>
                        <input type="hidden" name="idOtp" value="${requestScope.randomIdOtp}">
                    </div>

                    <div class="field">
                        <button type="submit" class="btn-primary">Xác nhận</button>
                    </div>

                    <div class="field">
                        <h6 class="error-message">${invalidOTP}</h6>
                    </div>
                </form>

                <form action="resendemail" method="post">
                    <div class="field">
                        <h6>Chưa nhận được mã? <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer;">Gửi lại</button></h6>
                        <input type="hidden" name="action" value="resend">
                        <input type="hidden" name="mail" value="${requestScope.email}">
                        <input type="hidden" name="idOtp" value="${requestScope.randomIdOtp}">
                    </div>
                    <div class="field">
                        <h6 style="color: green;">${resentSuccess}</h6>
                    </div>
                </form>

                <div id="successPopup" style="display: none; position: fixed; top: 52%; left: 50%; background-color: #ffffff; transform: translate(-50%, -50%); padding: 20px; text-align: center;">
                    <h3 style="color: green;">${verifySuccess}</h3>
                    <button class="btn-primary" onclick="redirectToLogin()">OK</button>
                </div>
            </div>
        </div>

        <script>
            // Countdown timer for OTP validity
            window.onload = function () {
                let countdownNumber = 60;
                const countdownElement = document.getElementById('countdown');
                const countdown = setInterval(() => {
                    countdownNumber--;
                    countdownElement.textContent = countdownNumber;
                    if (countdownNumber <= 0) {
                        clearInterval(countdown);
                        countdownElement.textContent = "Hết giờ!";
                    }
                }, 1000);
            };

            // Redirect to login page after OTP verification success
            function redirectToLogin() {
                window.location.href = "login.jsp";
            }

            // Display success message if present
            document.addEventListener("DOMContentLoaded", function () {
                const verifySuccessMessage = "${verifySuccess}";
                if (verifySuccessMessage !== "") {
                    document.getElementById("successPopup").style.display = "block";
                }
            });
        </script>
    </body>
</html>
