<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="feed-container">
	<div class="feed-item-list">
	    <c:if test="${not empty feedItemList}">
	        <c:forEach items="${feedItemList}" var="feedItem">
	            <div class="item">
	                <div class="feed-logo" style="background-image: url(${feedItem.imageUrl});"></div>
	                <div class="feed-content">
		                <div class="feed-info">
		                    <div class="title">
		                        <span onclick="addFeedTab(${feedItem.id})">${feedItem.title}</span>
		                    </div>
		                    <div class="summary">
		                        ${feedItem.summary}
		                    </div>
		                </div>
		                <div class="pub-info">
		                    ${feedItem.feedSource.name} | 
		                    <c:if test="${not empty feedItem.author}">
		                        by <i>${feedItem.author}</i> | 
		                    </c:if>
		                    ${feedItem.publishedDateString}
		                </div>
	                </div>
	            </div>
	        </c:forEach>
	    </c:if>
	</div>
	   
	<div class="feed-tab-list">
	    <c:if test="${not empty feedTabList}">
	        <c:forEach items="${feedTabList}" var="feedTab">
	            <%@ include file="tab.jsp" %>
	        </c:forEach>
	    </c:if>
	</div>
</div>