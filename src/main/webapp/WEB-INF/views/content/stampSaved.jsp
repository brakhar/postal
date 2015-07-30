<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<script type="text/javascript" src="<c:url value="/resources/js/stampUtils.js"/>"></script>
		<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
	</head>
	<body>
		<script type="text/javascript">

			function closeDialog(){
				window.parent.$('.ui-dialog-content:visible').dialog('close')
			}
			//refreshStampTable();
			closeDialog();
		</script>
	</body>
</html>

