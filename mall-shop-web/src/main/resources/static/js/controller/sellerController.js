 //控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
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
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.add=function(){
		sellerService.add( $scope.entity ).success(
			function(response){
				if(response.code == "0000"){
					//注册成功，跳转到登录页
                    location.href = "shoplogin.html";
                }else {
                    alert(response.message);
                }
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
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
		sellerService.search(page,rows,$scope.searchEntity).success(
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
