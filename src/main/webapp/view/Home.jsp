<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Home Page</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet"/>
</head>
<body class="font-roboto bg-gray-100 min-h-screen flex flex-col">
<jsp:include page="Navbar.jsp"/>

<main class="flex-grow my-8">
    <div class="container mx-auto px-6">
        <h3 class="text-gray-700 text-2xl font-medium">Products</h3>
        <div class="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <c:forEach var="p" items="${requestScope.products}">
                <div class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden" style="cursor: pointer"  onclick="window.location.href='${pageContext.request.contextPath}/product-detail?id=${p.id}'">
                    <div class="flex items-end justify-end h-56 w-full bg-cover" style="background-image: url('${p.image_url}');">
                    </div>
                    <div class="px-5 py-3">
                        <h3 class="text-gray-700 uppercase">${p.name}</h3>
                        <p class="text-gray-500 mt-2">${p.price} $</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>


</main>
<div class="mt-8 flex justify-center">
    <nav class="inline-flex">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}" class="px-4 py-2 mx-1 text-gray-600 bg-white border rounded-md hover:bg-gray-200">
                <i class="fas fa-chevron-left"></i>
            </a>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="?page=${i}"
               class="px-4 py-2 mx-1 border rounded-md
               <c:if test='${currentPage == i}'>bg-blue-500 text-white</c:if>
               hover:bg-gray-200">
                    ${i}
            </a>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}" class="px-4 py-2 mx-1 text-gray-600 bg-white border rounded-md hover:bg-gray-200">
                <i class="fas fa-chevron-right"></i>
            </a>
        </c:if>
    </nav>
</div>
<!-- Footer -->
<footer class="bg-white shadow-md py-4 mt-8">

    <div class="container mx-auto px-6 flex justify-between items-center">
        <span class="text-gray-600 text-sm">© 2025 OSS by Group4. All rights reserved.</span>
        <div class="flex space-x-3">
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-facebook-f"></i></a>
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-twitter"></i></a>
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-instagram"></i></a>
        </div>
    </div>
</footer>

</body>
</html>
