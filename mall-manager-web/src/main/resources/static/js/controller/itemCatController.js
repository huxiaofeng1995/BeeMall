 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService, typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				if(response.code == "0000"){
                    $scope.list=response.data.list;
                    $scope.paginationConf.totalItems=response.data.total;//更新总记录数
                }else {
                    alert("请求失败");
                }
			}			
		);
	}

	$scope.parentId = 0;

	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
            var obj = $scope.entity.typeId;//select2选中元素后传过来是一个对象
            $scope.entity.typeId = obj.id;
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
            $scope.entity.parentId=$scope.parentId;//赋予上级ID
            var obj = $scope.entity.typeId;//select2选中元素后传过来是一个对象
            $scope.entity.typeId = obj.id;
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.code == "0000"){
                    $scope.findByParentId($scope.parentId);//重新加载
                }else {
                    alert(response.message);
                }
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){
        if(confirm("确定要删除吗？")) {
            //获取选中的复选框
            itemCatService.dele($scope.selectedIds).success(
                function (response) {
                    if (response.code == "0000") {
                        $scope.findByParentId($scope.parentId);//重新加载
                    } else {
                        alert(response.message)
                    }
                }
            );
        }
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				if(response.code == "0000"){
                    $scope.list=response.data.list;
                    $scope.paginationConf.totalItems=response.data.total;//更新总记录数
                }else {
                    alert(response.message);
                }
			}			
		);
	}

    //根据上级ID显示下级列表
    $scope.findByParentId=function(parentId){

        $scope.parentId=parentId;//记住上级ID

        itemCatService.findByParentId(parentId).success(
            function(response){
                if(response.code == "0000") {
                    $scope.list = response.data;
                }else {
                    alert(response.message);
				}
            }
        );
    }

    //面包屑（最多只有3层）
    $scope.grade=1;//默认为1级
    //设置级别
    $scope.setGrade=function(value){
        $scope.grade=value;
    }
    //读取列表
    $scope.selectList=function(p_entity){
        if($scope.grade==1){//如果为1级
            $scope.entity_1=null;
            $scope.entity_2=null;
        }
        if($scope.grade==2){//如果为2级
            $scope.entity_1=p_entity;
            $scope.entity_2=null;
        }
        if($scope.grade==3){//如果为3级
            $scope.entity_2=p_entity;
        }
        $scope.findByParentId(p_entity.id);	//查询此级下级列表
    }

    $scope.templateList = {data: []};//品牌列表
    //读取品牌列表
    $scope.findTemplateList = function () {
        typeTemplateService.selectOptionList().success(
            function (response) {
                if (response.code == "0000") {
                    $scope.templateList = {data: response.data};
                }
            }
        );
    }

});	
