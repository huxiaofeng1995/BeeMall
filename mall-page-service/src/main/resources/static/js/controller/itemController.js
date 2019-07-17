
app.controller("itemController",function($scope, $http){
    $scope.num = 1;
	
	//数量操作
	$scope.addNum=function(x){
		$scope.num=$scope.num+x;
		if($scope.num<1){
			$scope.num=1;
		}
	}

	$scope.specificationItems={};//记录用户选择的规格
	//用户选择规格
	$scope.selectSpecification=function(name,value){	
		$scope.specificationItems[name]=value;
		searchSku();//读取sku
	}	
	//判断某规格选项是否被用户选中
	$scope.isSelected=function(name,value){
		if($scope.specificationItems[name]==value){
			return true;
		}else{
			return false;
		}		
	}
	
	$scope.sku={};//当前选择的sku
	
	//加载默认的sku
	$scope.loadSku=function(){
		$scope.sku = skuList[0];
		$scope.specificationItems = JSON.parse(JSON.stringify($scope.sku.spec));//深克隆
	}
	
	//匹配两个对象
	matchObject=function(map1,map2){		
		for(var k in map1){
			if(map1[k]!=map2[k]){
				return false;
			}			
		}
		for(var k in map2){//交换位置再比较一次
			if(map2[k]!=map1[k]){
				return false;
			}			
		}
		return true;		
	}

	//查询SKU
	searchSku=function(){
		for(var i=0;i<skuList.length;i++ ){
			if( matchObject(skuList[i].spec ,$scope.specificationItems ) ){
				$scope.sku=skuList[i];
				return ;
			}			
		}	
		$scope.sku={id:0,title:'--------',price:0};//如果没有匹配的		
	}

	//添加商品到购物车
	$scope.addToCart=function(){
        $http.get('http://localhost:9013/cart/addGoodsToCartList?itemId='
            + $scope.sku.id +'&num='+$scope.num,{'withCredentials':true}).success(//必须在AJAX请求中打开withCredentials属性。否则，即使服务器同意发送Cookie，浏览器也不会发送
            function(response){
                if(response.code == "0000"){
                    location.href='http://localhost:9013/cart.html';//跳转到购物车页面
                }else{
                    alert(response.message);
                }
            }
        );
    }

});
