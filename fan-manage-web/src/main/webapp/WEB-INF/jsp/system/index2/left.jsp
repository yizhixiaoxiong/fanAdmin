<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--左侧菜单，left 使用组件 sidebar-->
		<div class="sidebar" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<!-- 左侧菜单上方的四个按钮 -->
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="icon-signal"></i>
					</button>
					<button class="btn btn-info">
						<i class="icon-pencil"></i>
					</button>

					<button class="btn btn-warning">
						<i class="icon-group"></i>
					</button>

					<button class="btn btn-danger">
						<i class="icon-cogs"></i>
					</button>
				</div>
				<!-- 菜单隐藏起来的图标 -->
				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span> 
					<span class="btn btn-info"></span>

					<span class="btn btn-warning"></span> 
					<span class="btn btn-danger"></span>
				</div>
				<!--左侧菜单-->
				<ul class="nav nav-list" id="menu" style="text-align: left;"></ul>


				<!-- /.nav-list -->
				<!-- 左侧菜单的下的可以隐藏菜单的小图标-->
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
		</div>
		<!-- 左侧完结-->