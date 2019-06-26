//基本控制层
app.controller('baseController' ,function($scope) {
    //重新加载列表 数据
    $scope.reloadList = function () {
        $scope.selectedIds = [];
        //切换页码
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };
    //删除
    $scope.selectedIds = [];
    $scope.updateSelection = function($event,id) {
        if($event.target.checked){
            $scope.selectedIds.push(id)
        }else {
            var index = $scope.selectedIds.indexOf(id);
            $scope.selectedIds.splice(index, 1);
        }

    }

    //全选按钮
    $scope.checkAll = function($event) {
        var checks = document.getElementsByClassName("testCheck")
        if($event.target.checked){

            for(var i = 0; i < checks.length; i++) {
                checks[i].checked = true;
            }
            for(var i = 0; i < $scope.list.length; i++){
                $scope.selectedIds.push($scope.list[i].id)//全选将当前页所有id加入被选中id数组中
            }
        }else {
            for(var i = 0; i < checks.length; i++) {
                checks[i].checked = false;
            }
            $scope.selectedIds = [];
        }
        //console.log($scope.selectedIds);
    }

    //提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
    $scope.jsonToString=function(jsonString,key) {
        if (jsonString != null){
            var json = JSON.parse(jsonString);//将json字符串转换为json对象
            var value = "";
            for (var i = 0; i < json.length; i++) {
                if (i > 0) {
                    value += ","
                }
                value += json[i][key];
            }
            return value;
        }else {
            return "";
        }
    }

    //从集合中按照key查询对象
    $scope.searchObjectByKey=function(list,key,keyValue){
        for(var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }

});