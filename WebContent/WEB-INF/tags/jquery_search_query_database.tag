<script>
	$(".autocomplete").autocomplete({
		source : function(request, response) {
			console.log(request.term);
			$.ajax({
				dataType : "json",
				type : 'GET',
				url : "searchbooks",
				data : {
					q : request.term
				},
				success : function(data) {
					console.log(data);
					response($.map(data, function(value, key) {
						console.log(value.bookId);

						return {
							value : value.title,
							avatar : value.image,
							label : value.title,
							bookId : value.bookId,
							author : value.author.name,
							title : value.title

						};
					}));
				}
			});
		},
		minLength : 3
	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		console.log(item)
		var inner_html = '<a href="book?id='
				+ item.bookId
				+ '&title='
				+ item.title
				+ '"><div class="list_item_container"><span class="image"><img style="width: 70px; text-align: left" src="' + item.avatar + '"></span><span style="font-size: 18px;" class="description">  '
				+ item.label + " <sub> by " + item.author
				+ '</sub></span></div></a><hr/>';
		return $("<li></li>").data("ui-autocomplete-item", item).append(
				inner_html).appendTo(ul)
	};
</script>
