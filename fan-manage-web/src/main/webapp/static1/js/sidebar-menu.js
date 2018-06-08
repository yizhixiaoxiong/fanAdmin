/*这个方法的是左边菜单选项，每个菜单在创建的时候，需要给定一个参数
用来表示菜单的前面图标，这个是创建菜单的时候就需要创建的，所以这个方法
需要优化*/
/*这个组件的另一个问题，二级菜单是i标签+文字，没有span标签，需要进行判断*/
(function ($) {
 $.fn.sidebarMenu = function (options) {
 options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
 var target = $(this);
 target.addClass('nav');
 target.addClass('nav-list');
 if (options.data) {
  init(target, options.data);
 }
 else {
  if (!options.url) return;
  $.getJSON(options.url, options.param, function (data) {
  init(target, data);
  });
 }
 var url = window.location.pathname;
 //menu = target.find("[href='" + url + "']");
 //menu.parent().addClass('active');
 //menu.parent().parentsUntil('.nav-list', 'li').addClass('active').addClass('open');
 function init(target, data) {
  $.each(data, function (i, item) {
  var li = $('<li id="li_'+item.id+'"></li>');
  var a = $('<a></a>');
  var icon = $('<i></i>');
  //icon.addClass('glyphicon');
  icon.addClass(item.icon);
  var text = $('<span></span>');
  text.addClass('menu-text').text(item.text);
  a.append(icon);
  a.append(text);
  if (item.menus&&item.menus.length>0) {
   a.attr('href', '#');
   a.addClass('dropdown-toggle');
   var arrow = $('<b></b>');
   arrow.addClass('arrow').addClass('icon-angle-down');
   a.append(arrow);
   li.append(a);
   var menus = $('<ul></ul>');
   menus.addClass('submenu');
   init(menus, item.menus);
   li.append(menus);
  }
  else {
   var href = 'javascript:addTabs({id:\'' + item.id + '\',title: \'' + item.text + '\',close: true,url: \'' + item.url + '\'});';
   a.attr('href', href);
   //if (item.istab)
   // a.attr('href', href);
   //else {
   // a.attr('href', item.url);
   // a.attr('title', item.text);
   // a.attr('target', '_blank')
   //}
   li.append(a);
  }
  target.append(li);
  });
 }
 }
 
 $.fn.sidebarMenu.defaults = {
 url: null,
 param: null,
 data: null
 };
})(jQuery);