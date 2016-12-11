    
    var app = angular.module('pos', []);
            app.controller('posCon', function ($scope,$http) {
                
                $http.get("/Market_Basket_Analysis/basketanalysis/pos")
                        .then(function (response) {
                            $scope.productslist = response.data;
                        });
                
                $scope.deleteProduct = function(id){
                   
                    $scope.currentproductid=id;
                    
                };
                        
            });
            