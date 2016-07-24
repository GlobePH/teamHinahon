angular.module('starter.controllers', [])

    .controller('AppCtrl', function ($scope, $ionicModal, $timeout) {

        // With the new view caching in Ionic, Controllers are only called
        // when they are recreated or on app start, instead of every page change.
        // To listen for when this page is active (for example, to refresh data),
        // listen for the $ionicView.enter event:
        //$scope.$on('$ionicView.enter', function(e) {
        //});

        // Form data for the login modal
        $scope.loginData = {};

        // Create the login modal that we will use later
        $ionicModal.fromTemplateUrl('templates/login.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.modal = modal;
        });

        // Triggered in the login modal to close it
        $scope.closeLogin = function () {
            $scope.modal.hide();
        };

        // Open the login modal
        $scope.login = function () {
            $scope.modal.show();
        };

        // Perform the login action when the user submits the login form
        $scope.doLogin = function () {
            console.log('Doing login', $scope.loginData);

            // Simulate a login delay. Remove this and replace with your login
            // code if using a login system
            $timeout(function () {
                $scope.closeLogin();
            }, 1000);
        };
    })

    .controller('IncidentController', function ($scope) {

        $scope.incidents = [
            {title: 'I\'m on fire', id: 1, body: "Lorem ipsum dolor sit amet"},
            {title: 'Vehicular Accident', id: 1, body: "Lorem ipsum dolor sit amet"}
        ];

        document.addEventListener('onSMSArrive', function (e) {
            var data = e.data;
            console.log(JSON.stringify(data));

            alert(JSON.stringify(data))
        });


        //$scope.viewDetails = function(id) {
        //  $location.path('app/playlists/' + id);
        //}

    })


    .controller('PlaylistCtrl', function ($scope, $stateParams) {

        $scope.results = $stateParams.result;

        console.log(JSON.stringify($stateParams));
    });
