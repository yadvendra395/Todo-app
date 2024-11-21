<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
	<h1>Enter Todo Details:</h1>
	<form:form method="post" modelAttribute="todo">
	
	<!--  path gives the value from the backing form object(todo) -->
	<!-- fieldset tag is used to group a no. of tags related to same thing... -->
	
	<fieldset class="mb-2">
		<form:label path="description">Description: </form:label>
		<form:input type="text" path="description" required="required"/>
		<form:errors cssClass="text-warning" path="description"/>
	</fieldset>
	
	<fieldset class="mb-2">
		<form:label path="targetDate">Target Date: </form:label>
		<form:input type="text" path="targetDate" required="required"/>
		<form:errors cssClass="text-warning" path="targetDate"/>
	</fieldset>
  	 
			<form:input type="hidden" path="id"/>
			<form:input type="hidden" path="done"/>
		<input type="submit" class="btn btn-success"/>
	</form:form>
</div>
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
	$('#targetDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>
<%@ include file="common/footer.jspf" %>