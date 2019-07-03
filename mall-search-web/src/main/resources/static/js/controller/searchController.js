app.controller('searchController',function($scope,searchService){

    $scope.searchMap = {category: '', brand: '', spec: {}}

    //搜索
    $scope.search=function(){
        searchService.search( $scope.searchMap ).success(
            function(response){
                $scope.resultMap=response.data;//搜索返回的结果
                console.log($scope.resultMap)
            }
        );
    }

    $scope.addSearchItem = function(key, value){
        if(key == 'category' || key == 'brand'){
            $scope.searchMap[key] = value;
        }else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();//每次增加搜索条件，就从后端查询一次新的结果
    }

    $scope.removeSearchItem = function (key) {
        if(key == 'category' || key == 'brand'){
            $scope.searchMap[key] = '';
        }else {
            delete $scope.searchMap.spec[key]
        }
        $scope.search();//每次删除搜索条件，就从后端查询一次新的结果
    }
});
