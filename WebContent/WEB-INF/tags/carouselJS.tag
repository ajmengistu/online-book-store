
<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<script src="/online-book-store/css/jquery.min.js"></script>
<script src="/online-book-store/css/owl.carousel.js"></script>
<script>
	$('.owl-theme').owlCarousel({
		loop : true,
		margin : 10,
		autoplayHoverPause : true,
		autoplay : true,
		nav : true,
		navText : [ "prev", "next" ],
		responsive : {
			0 : {
				items : 1
			},
			600 : {
				items : 3
			},
			1000 : {
				items : 5
			}
		}
	})
</script>