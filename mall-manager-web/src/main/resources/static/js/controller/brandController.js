app.controller('brandController',function($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});//继承

    $scope.findAll = function() {
        brandService.findAll().success(function(resp) {
            $scope.list = resp;
        })
    }



    //分页
    $scope.findPage = function(page,size){
        brandService.findPage(page,size).success(
            function(response){
                if(response.code == "0000"){
                    $scope.list=response.data.list;
                    $scope.paginationConf.totalItems=response.data.total;//更新总记录数
                }else {
                    alert("请求失败");
                }
            });
    };

    //新增    修改
    $scope.saveBrand = function() {
        var serviceObject;//服务层对象
        if($scope.entity.id != null){
            serviceObject = brandService.update($scope.entity)
        }else {
            serviceObject = brandService.add($scope.entity)
        }
        serviceObject.success(
            function(response) {
                if(response.code == "0000"){
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
            }
        )
    }

    $scope.setEntity = function(val) {
        $scope.entity = val;
    }

    $scope.del = function() {
        if(confirm("确定要删除吗？")){
            brandService.del($scope.selectedIds).success(
                function(response){
                    if(response.code == "0000"){
                        $scope.reloadList();//刷新列表
                    }
                }
            );
        }
    }

    //条件搜索
    $scope.searchEntity = {};//定义搜索对象
    $scope.search = function(page,size){
        //console.log($scope.searchEntity)
        brandService.search(page, size, $scope.searchEntity).success(
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

})