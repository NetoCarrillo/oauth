angular.module("app", [])
	.controller("home", function($http, $location){
		var self = this;
		$http.get("/user").success(function(data){
			self.authenticated = true;
			self.user = data.userAuthentication.details.name;
		}).error(function(){
			self.authenticated = false;
			self.user = "N/A";
		});

		self.logout = function(){
			if($location.absUrl().indexOf("error=true") >= 0){
				self.authenticated = false;
				self.error = true;
			}

			$http.post('/logout', {}).success(function(){
				self.authenticated = false;
				$location.path('/');
			}).error(function(data){
				self.authenticated = false;
				console.log("Logout failed");
			});
		};
	});
