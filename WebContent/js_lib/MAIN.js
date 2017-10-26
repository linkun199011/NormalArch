MainWindow = function() { 
	var rootNode = new Ext.tree.AsyncTreeNode();

	// 根节点
	var caidan = new Ext.tree.TreeNode({id:"",tree:""});
	
	// 一级菜单
	var node1 = new Ext.tree.TreeNode({id:'testAutoComplete.jsp',text:'系统信息管理',leaf:true});
	
	var node2 = new Ext.tree.TreeNode({id:'1',text:'血源关系管理'});
	var node2_1 = new Ext.tree.TreeNode({id:'addAppCoop.jsp',text:'应用协作新增',leaf:true});
	var node2_2 = new Ext.tree.TreeNode({id:'editAppCoop.jsp',text:'应用协作维护',leaf:true});
	node2.appendChild(node2_1);
	node2.appendChild(node2_2);	
	
	var node3 = new Ext.tree.TreeNode({id:'draw_test.jsp',text:'关联关系展示'});
	var node3_1 = new Ext.tree.TreeNode({id:'draw_test.jsp',text:'关联图展示',leaf:true});
	var node3_2 = new Ext.tree.TreeNode({id:'appRel.jsp',text:'服务检索',leaf:true});
	node3.appendChild(node3_1);
	node3.appendChild(node3_2);	
	
	caidan.appendChild(node1);
	caidan.appendChild(node2);
	caidan.appendChild(node3);

	//function menu
	var tabslimit=7;
    var menuPanel = new Ext.tree.TreePanel({
        id:'ums-tree',
    	iconCls:'fun',
    	title:'功能管理',
    	region:'west',
		width: 200,
		minSize: 200,
		maxSize: 200,
		split:true,
		margins:'5 0 5 5',
		cmargins:'5 5 5 5',
		autoScroll:true,
		layoutConfig:{
           animate:true
        },
		layout:'accordion',
		rootVisible: false,
		root:caidan,
		listeners :{
        	//菜单点击操作
			click: function (node ,e){
        		if(isNaN(node.id)){
        			var mainPanels  = Ext.getCmp("mainPanelID");
        			var panel = mainPanels.findById(node.id);
        			if(panel!=null){
        				panel.show();
        				return;
        			}
        			var mainPanel = new Ext.Panel({
        				id:node.id,
        				title:node.text,
        				region:'center',
        				margins:'5 5 5 0',
        				activeTab:0,
        				html:'<iframe src="'+node.id+'" name="main" frameborder="0" width="100%" height="100%"></iframe>',
        				resizeTabs:true,
        				autoScroll: true,
        				layoutOnTabChange:true,
        				closable: true
        			});
        			if(mainPanels.items.length<tabslimit)        			
        			{
        				mainPanels.add(mainPanel).show();
        			}
        			else
        			{
        			  
		              jQuery.messager.show(0, '最多同时打开'+tabslimit+'个窗口，请关闭后重新打开', 3000);
	                  
        			}
        		}
			}
		},
		tbar:[{
				iconCls:"tree-expand",tooltip:'全部展开',
				handler:function(){menuPanel.getRootNode().expand(true);}
			},{
				iconCls:"tree-collapse",tooltip:'全部收拢',
				handler:function(){menuPanel.getRootNode().collapse(true);}
			}
		]
    });
	//main tab	
    var mainPanel = new Ext.TabPanel({
		id:'mainPanelID',
		region:'center',
		margins:'5 5 5 0',
		activeTab:0,
		resizeTabs:true,
		autoScroll: true,
		layoutOnTabChange:true,
		plugins: new Ext.ux.TabCloseMenu()
	});
    var indexPanel = new Ext.Panel({
		id:'index',
		title:'首页',
		region:'center',
		margins:'5 5 5 0',
		activeTab:0,
		html:'<iframe src="welcome.jsp" name="main" frameborder="0" width="100%" height="100%"></iframe>',
		resizeTabs:true,
		autoHeight:true,
		autoScroll: true,
		layoutOnTabChange:true
	});
    mainPanel.add(indexPanel).show();
	
	//Config Params
	var cfg = {
		layout : 'border',
		items:[
			{region:'north',border:false,contentEl:'mainTopPanel',height:'96'},
			menuPanel,mainPanel
		]
	};
	//call father constructor
	MainWindow.superclass.constructor.call(this, cfg);
}


function addTab(url,txt){
	var mainPanels  = Ext.getCmp("mainPanelID");
	var panel = mainPanels.findById(url);         
		if(panel!=null){				
			panel.show();
			return;
		}
	
	var mainPanel = new Ext.Panel({
		id:url,
		title:txt,
		region:'center',
		margins:'5 5 5 0',
		activeTab:0,
		height:'100%',
		html:'<iframe src="'+url+'" name="main" frameborder="0" width="100%" height="100%"></iframe>',
		resizeTabs:true,
		autoScroll: true,
		layoutOnTabChange:true,
		closable: true
		
	});
	if(mainPanels.items.length<7){
	 mainPanels.add(mainPanel).show();
	 }else{
	 jQuery.messager.show(0, '最多同时打开7个窗口，请关闭后重新打开', 3000);
	 }
}

function removeTab(){
	var mainPanels  = Ext.getCmp("mainPanelID");
	var mainPanel = mainPanels.find("region","center");
	for(var i=0; i<mainPanel.length; i++){
		if(mainPanel[i].isVisible()){
			mainPanels.remove(mainPanel[i]);
			break;
		}
	}
}

Ext.extend(MainWindow, Ext.Viewport);