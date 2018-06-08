<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--左侧菜单，left 使用组件 sidebar-->
	<div class="sidebar" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>

			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
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

				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span>

					<span class="btn btn-info"></span>

					<span class="btn btn-warning"></span>

					<span class="btn btn-danger"></span>
				</div>
			</div><!-- #sidebar-shortcuts -->

			<ul class="nav nav-list">
				<li class="active">
					<a href="index.html">
						<i class="icon-dashboard"></i>
						<span class="menu-text"> 控制台 </span>
					</a>
				</li>

				<li>
					<a href="typography.html">
						<i class="icon-text-width"></i>
						<span class="menu-text"> 文字排版 </span>
					</a>
				</li>

				<li>
					<a href="#" class="dropdown-toggle">
						<i class="icon-desktop"></i>
						<span class="menu-text"> UI 组件 </span>

						<b class="arrow icon-angle-down"></b>
					</a>

					<ul class="submenu">
						<li>
							<a href="elements.html">
								<i class="icon-double-angle-right"></i>
								组件
							</a>
						</li>

						<li>
							<a href="buttons.html">
								<i class="icon-double-angle-right"></i>
								按钮 &amp; 图表
							</a>
						</li>

						<li>
							<a href="treeview.html">
								<i class="icon-double-angle-right"></i>
								树菜单
							</a>
						</li>

						<li>
							<a href="jquery-ui.html">
								<i class="icon-double-angle-right"></i>
								jQuery UI
							</a>
						</li>

						<li>
							<a href="nestable-list.html">
								<i class="icon-double-angle-right"></i>
								可拖拽列表
							</a>
						</li>

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-double-angle-right"></i>

								三级菜单
								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="#">
										<i class="icon-leaf"></i>
										第一级
									</a>
								</li>

								<li>
									<a href="#" class="dropdown-toggle">
										<i class="icon-pencil"></i>

										第四级
										<b class="arrow icon-angle-down"></b>
									</a>

									<ul class="submenu">
										<li>
											<a href="#">
												<i class="icon-plus"></i>
												添加产品
											</a>
										</li>

										<li>
											<a href="#">
												<i class="icon-eye-open"></i>
												查看商品
											</a>
										</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
				</li>

			</ul><!-- /.nav-list -->

			<div class="sidebar-collapse" id="sidebar-collapse">
				<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
			</div>

			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
			</script>
		</div>
		<!-- 左侧完结-->