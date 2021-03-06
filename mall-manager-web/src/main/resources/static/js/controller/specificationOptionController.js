 //控制层 
app.controller('specificationOptionController' ,function($scope,$controller   ,specificationOptionService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		specificationOptionService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		specificationOptionService.findPage(page,rows).success(
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
	
	//查询实体 
	$scope.findOne=function(id){				
		specificationOptionService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=specificationOptionService.update( $scope.entity ); //修改  
		}else{
			serviceObject=specificationOptionService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.code == "0000"){
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		specificationOptionService.dele( $scope.selectedIds ).success(
			function(response){
				if(response.success){
					if(response.code == "0000"){
                        $scope.reloadList();//刷新列表
                    }
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		specificationOptionService.search(page,rows,$scope.searchEntity).success(
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
    
});	
