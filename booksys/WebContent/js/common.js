function pagebind(info){
	
	$(".page-spliter").empty();
	
	var html = '';
	var prePage = info.currentPage - 1;
	
	if(prePage <= 0){
		html += '<a href="javascript:loadData(1)">&lt;</a>';
	}else{
		html += '<a href="javascript:loadData(' + prePage + ')">&lt;</a>';
	}
	html = '<a href="#">&lt;</a>';
	html += '<a href="javascript:loadData(1)">首页</a>';
	for(var i = 1; i <= info.totalPage; i++){
		if(i == info.currentPage){
			html += '<span class="current">' + i + '</span>';
		}else{
			html += '<a href="javascript:loadData(' + i + ')">' + i + '</a>';
		}
	}
	html += '<a href="javascript:loadData(' + info.totalPage + ')">尾页</a>';
	var nextPage = info.currentPage + 1;
	if(nextPage > info.totalPage){
		html += '<a href="javascript:loadData(' + info.totalPage + ')">&gt;</a>';
	}else{
		html += '<a href="javascript:loadData(' + nextPage + ')">&gt;</a>';
	}
	$(".page-spliter").append($(html));
}