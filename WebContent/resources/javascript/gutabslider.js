(function($) {
    $.fn.gutabslider = function(options) {

        var mainClassName = "gu-tab-slider";
        var barClassName = "gu-sliding-bar";

        var positionTabUnderCurrentActiveTab = function($element) {
            var $activeTab = $element.find("li.active a");
            if (_.any($activeTab)) {
                positionTabUnderline($element, $activeTab);
            }
        };

        var positionTabUnderline = function($element, $tab) {
            var $slidingBar = $element.find("." + barClassName);
            //Set left
            var left = $tab.position().left;
            $slidingBar.css("left", left + "px");

            //Set top
            var tabHeight = $tab.outerHeight();
            var top = $tab.position().top;
            $slidingBar.css("top", (top + tabHeight) + "px");

            //Set width
            var width = $tab.outerWidth();
            $slidingBar.css("width", width + "px");
        }

        return this.each(function(index, element) {
            $element = $(element);

            //Don't reapply
            if ($element.hasClass(mainClassName)) {
                if (options === "active-tab-changed") {
                    positionTabUnderCurrentActiveTab($element);
                }

                return true;
            }

            //Add sliding bar
            $element.append("<span class='" + barClassName + "'></span>");
            var $slidingBar = $element.find("." + barClassName);
            $slidingBar.css("position", "absolute");
            $slidingBar.css("display", "block");
            $slidingBar.css("width", "0");
            $slidingBar.css("height", "3px");

            //Position tab under currently active tab
            positionTabUnderCurrentActiveTab($element);

            //Resize when window size changes
            $(window).resize(_.debounce(function() {
                positionTabUnderCurrentActiveTab($element);
            }, 100));

            //Finished
            $element.addClass(mainClassName);
        });
    };
})(jQuery);