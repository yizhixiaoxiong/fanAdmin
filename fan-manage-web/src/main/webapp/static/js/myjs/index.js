
function cmainFrame(){
	var hmain = document.getElementById("mainFrame");
	var bheight = document.documentElement.clientHeight;
	hmain .style.width= '100%';
	hmain .style.height=(bheight-49)+'px';
	//这个应该是加载的图片
	var bkbgjz = document.getElementById("bkbgjz");
	bkbgjz.style.height=(bheight-41)+'px';
	
}
cmainFrame();
window.onresize=function(){  
	cmainFrame();
};
jzts();