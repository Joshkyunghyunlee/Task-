var app = angular.module('Morrangu', ['ui.router']); //[] contains dependencies

//In angular, factory and service are related --> instances of provider
app.factory('posts', [function() {  //create a factory for posts
    var o = {
        posts: []
    };
    return o;
}]);

app.controller('MainCtrl', [
'$scope',
'posts',
function($scope, posts){ //inject our posts service
  $scope.test = 'Hello world!';
  $scope.posts = posts.posts;
  $scope.posts = [
  {title: 'post 1', upvotes: 5},
  {title: 'post 2', upvotes: 2},
  {title: 'post 3', upvotes: 15},
  {title: 'post 4', upvotes: 9},
  {title: 'post 5', upvotes: 4}
];
    
  $scope.addPost = function(){
  if(!$scope.title || $scope.title === '') {
      return;
  }
      
  $scope.posts.push({
      title: $scope.title, 
      link: $scope.link,
      upvotes: 0,
      comments: [
          {author: 'Joe', body: 'Cool post!', upvotes: 0},
          {auther: 'Josh', body: 'Such a great idea!', upvotes: 3}
          ]
  });
  
  $scope.title = '';
  $scope.link = ''
  };
    
  $scope.incrementUpvotes = function(post) {
      post.upvotes += 1;
  }
}]);

    
app.controller('PostsCtrl', [
'$scope',
'$stateParams',
'posts',
function($scope, $stateParams, posts){
    
  $scope.addComment = function() {
      if($scope.body === '') {
          return;
      }
      $scope.post.comment.push ({
          body: $scope.body,
          author: 'user',
          upvotes: 0
      });
      $scope.body = '';
  };
}]);

//setup a homestate
app.config([
    '$stateProvider',
    '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
        $scope.post = posts.posts[$stateParams.id];
        
        $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: '/home.html',
            controller: 'MainCtrl'
        });
        .state('posts', {
            url: '/posts/{id}',
            templateUrl: '/posts.html',
            controller: 'PostsCtrl'
        });
        
        $urlRouterProvider.otherwise('home'); //redirect unspecified routes
}]);