app.controller('cartController' ,function($scope, cartService){
    //查询购物车列表
    $scope.findCartList=function(){
        cartService.findCartList().success(
            function(response){
                if(response.code == "0000"){
                    $scope.cartList = response.data.list;
                    $scope.loginName = response.data.username;
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

    //获取地址列表
    $scope.findAddressList = function(){
        cartService.findAddressList().success(
            function(response){
                if(response.code == "0000"){
                    $scope.addressList = response.data;
                    //设置默认地址
                    for(var i=0;i< $scope.addressList.length;i++){
                        if($scope.addressList[i].isDefault=='1'){
                            $scope.address=$scope.addressList[i];
                            break;
                        }
                    }
                }
            }
        );
    }

    //选择地址
    $scope.selectAddress=function(address){
        $scope.address=address;
    }

    //判断是否是当前选中的地址
    $scope.isSelectedAddress=function(address){
        if(address==$scope.address){
            return true;
        }else{
            return false;
        }
    }

    $scope.addAddress = function(){
        cartService.addAddress($scope.addr).success(
            function (response) {
                $scope.addr = {}
                $scope.findAddressList();
            }
        )
    }

    $scope.order={paymentType:'1'};
    //选择支付方式
    $scope.selectPayType=function(type){
        $scope.order.paymentType= type;
    }

});