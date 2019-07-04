//广告控制层（运营商后台）
app.controller("contentController",function($scope,contentService){
    $scope.contentList=[];//广告集合
    $scope.findByCategoryId=function(categoryId){
        contentService.findByCategoryId(categoryId).success(
            function(response){
                if(response.code == "0000") {
                    $scope.contentList[categoryId] = response.data;
                }else {
                    alert(response.message)
                }
            }
        );
    }

    $scope.search = function(){
        location.href = "http://localhost:9006/search.html#?keywords=" + $scope.keywords;
    }
});
