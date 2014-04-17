getCurrentModel = function(model) {
	return ko.dataFor(model);
};

getContextModel = function(model) {
	return ko.contextFor(model);
};

getParentModel = function(model) {
	return ko.contextFor(model).$parent;
};	