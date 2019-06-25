 //控制层 
app.controller('goodsController' ,function($scope,$controller   ,goodsService, uploadService, itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
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
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//增加商品
	$scope.add = function(){
		$scope.entity.goodsDesc.introduction = editor.html();
        $scope.entity.goodsDesc.itemImages = JSON.stringify($scope.entity.goodsDesc.itemImages)
        goodsService.add( $scope.entity ).success(
			function(response){
				if(response.code == "0000"){
                    alert("新增成功");
                    $scope.entity={goods:{},goodsDesc:{itemImages:[]}};
                    editor.html("")
                }else {
                    alert(response.message);
                }
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
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
		goodsService.search(page,rows,$scope.searchEntity).success(
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

    /**
     * 上传图片
     */
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(function(response) {
            if(response.code == "0000"){//如果上传成功，取出url
                $scope.image_entity.url=response.data.url;//设置文件地址
            }else{
                alert(response.message);
            }
        }).error(function() {
            alert("上传发生错误");
        });
    };

    $scope.entity={goods:{},goodsDesc:{itemImages:[]}};//定义页面实体结构

    //添加图片列表
    $scope.add_image_entity=function(){
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }

    //列表中移除图片
    $scope.remove_image_entity=function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //读取一级分类
    $scope.selectItemCat1List=function(){
        itemCatService.findByParentId(0).success(
            function(response){
                if(response.code == "0000") {
                    $scope.itemCat1List = response.data;
                }else {
                    alert(response.message);
				}
            }
        );
    }

    //读取二级分类
    $scope.$watch('entity.goods.category1Id', function(newValue, oldValue) {//监控
        //根据选择的值，查询二级分类
        itemCatService.findByParentId(newValue).success(
            function(response){
                if(response.code == "0000") {
                    $scope.itemCat2List = response.data;
                }else {
                    alert(response.message);
                }
            }
        );
    });
	//读取三级分类
    $scope.$watch('entity.goods.category2Id', function(newValue, oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.findByParentId(newValue).success(
            function(response){
                if(response.code == "0000") {
                    $scope.itemCat3List = response.data;
                }else {
                    alert(response.message);
                }
            }
        );
    });

    //三级分类选择后  读取模板ID
    $scope.$watch('entity.goods.category3Id', function(newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function(response){
                $scope.entity.goods.typeTemplateId=response.typeId; //更新模板ID
            }
        );
    });

});	
