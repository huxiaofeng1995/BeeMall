app.controller('cartController' ,function($scope, cartService){
    //查询购物车列表
    $scope.findCartList=function(){
        cartService.findCartList().success(
            function(response){
                if(response.code == "0000"){
                    $scope.cartList=response.data;
                    $scope.totalValue=cartService.sum($scope.cartList);//求合计数
                }else {
                    alert(response.message)
                }
            }
        );
    }

    //添加商品
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num).success(
            function (response) {
                if(response.code == "0000"){
                    $scope.findCartList();
                }else {
                    alert(response.message)
                }
            }
        )
    }

});