/*
jQWidgets v4.4.0 (2016-Nov)
Copyright (c) 2011-2016 jQWidgets.
License: http://jqwidgets.com/license/
*/

/*
* Depends:
*   jqxcore.js
*/

(function ($) {

    $.jqx.response = function () {
        this.defineInstance();
    }

    $.jqx.response.prototype = {

        defineInstance: function () {
            this._handlers = new Array();
            this.refresh();
            var that = this;
            this.layouts = [];
            this.addHandler($(document), 'scroll.jqxresponse', function () {
                that.scroll = that.getScroll();
            });
        },

        refresh: function()
        {
            this.os = this.getOS();
            this.browser = this.getBrowser();
            this.device = this.getDevice();
            this.viewPort = this.getViewPort();
            this.document = this.getDocument();
            this.scroll = this.getScroll();
            this.media = window.matchMedia || window.msMatchMedia || function () { return {}; };
        },

        refreshSize: function()
        {
            this.viewPort = this.getViewPort();
            this.document = this.getDocument();
        },

        addHandler: function (source, event, func, data) {
            switch (event) {
                case 'mousemove':
                    if (window.addEventListener && !data) {
                        source[0].addEventListener('mousemove', func, false);
                        return false;
                    }
                    break;
            }

            if (source.on) {
                source.on(event, func);
            }
            else {
                source.bind(event, func);
            }
        },

        removeHandler: function (source, event, func) {
            if (event == undefined) {
                if (source.off) {
                    source.off();
                }
                else source.unbind();
                return;
            }

            if (func == undefined) {
                if (source.off) {
                    source.off(event);
                }
                else {
                    source.unbind(event);
                }
            }
            else {
                if (source.off) {
                    source.off(event, func);
                }
                else {
                    source.unbind(event, func);
                }
            }
        },


        responsive: function(layoutObject)
        {
            if (!this._counter)
            {
                this._counter = 0;
            }

            var deviceType = this.getDevice().type;
            var rows = $(layoutObject.container);
            for (var i = 0; i < rows.length; i++)
            {
                var row = rows[i];
                row.style.position = "relative";
                row.style.clear = "both";
                var rowWidth = $(row).width();
                var columns = $(row).children();
                
                for (var j = 0; j < columns.length; j++)
                {
                    var column = columns[j];
                    var className = column.className;
                   
                    column.style.position = "relative";
                    column.style.top = "0px";
                    $(column).css("float", "left");
                    column._className = column.className;
                    if (layoutObject.colClass)
                    {
                        $(column).addClass(layoutObject.colClass);
                    }
                }
            }
            var resize = function ()
            {
               for (var i = 0; i < rows.length; i++)
                {
                    var row = rows[i];
                    var rowWidth = $(row).width();
                    var updateColumns = function (rowWidth)
                    {
                        var columns = $(row).children();
                        var margin = layoutObject.margin;
                        var padding = layoutObject.padding;
                        var offsets = layoutObject.colOffsets;
                        var totalWidth = rowWidth;
                        var left = 0;
                        for (var j = 0; j < columns.length; j++)
                        {
                            var column = columns[j];
                            column.className = column._className;
                            var className = column._className;
                            if (layoutObject.colClass)
                            {
                                if (className != "")
                                {
                                    className += " " + layoutObject.colClass;
                                }
                                else
                                {
                                    className = layoutObject.colClass;
                                }
                            }

                            var columnWidth = rowWidth / columns.length;
                            if (layoutObject.colWidths[j])
                            {
                                columnWidth = rowWidth * layoutObject.colWidths[j];
                            }
                            else
                            {
                                columnWidth = rowWidth / columns.length;
                            }

                            if (!layoutObject.deviceTypes || (layoutObject.deviceTypes && layoutObject.deviceTypes.indexOf(deviceType) >= 0))
                            {
                                for (var m = 0; m < layoutObject.breakpoints.length; m++)
                                {
                                    var breakpoint = layoutObject.breakpoints[m - 1];
                                    var breakpoint2 = layoutObject.breakpoints[m];

                                    if (m > 0 && (breakpoint.width >= rowWidth && breakpoint2.width < rowWidth))
                                    {
                                        if (breakpoint.colWidths[j])
                                        {
                                            columnWidth = rowWidth * breakpoint.colWidths[j];
                                            if (breakpoint.colClass)
                                            {
                                                if (column._className != "")
                                                {
                                                    className = column._className + " " + breakpoint.colClass;
                                                }
                                                else
                                                {
                                                    className = breakpoint.colClass;
                                                }

                                                column.className = className;
                                            }

                                            if (breakpoint.margin)
                                            {
                                                margin = breakpoint.margin;
                                            }
                                            else
                                            {
                                                breakpoint.margin = { left: 0, right: 0, top: 0, bottom: 0 };
                                                margin = breakpoint.margin;
                                            }
                                            if (breakpoint.padding)
                                            {
                                                padding = breakpoint.padding;
                                            }
                                            else
                                            {
                                                breakpoint.padding = { left: 0, right: 0, top: 0, bottom: 0 };
                                                padding = breakpoint.padding;
                                            }

                                            if (breakpoint.colOffsets)
                                            {
                                                offsets = breakpoint.colOffsets;
                                            }
                                            else
                                            {
                                                breakpoint.colOffsets = new Array(columns.length);
                                                offsets = breakpoint.colOffsets;
                                            }
                                        }
                                    } else if (rowWidth <= breakpoint2.width)
                                    {
                                        if (breakpoint2.colWidths[j])
                                        {
                                            columnWidth = rowWidth * breakpoint2.colWidths[j];
                                            if (breakpoint2.colClass)
                                            {
                                                if (column._className != "")
                                                {
                                                    className = column._className + " " + breakpoint2.colClass;
                                                }
                                                else
                                                {
                                                    className = breakpoint2.colClass;
                                                }

                                                column.className = className;
                                            }
                                            if (breakpoint2.margin)
                                            {
                                                margin = breakpoint2.margin;
                                            }
                                            else
                                            {
                                                breakpoint2.margin = { left: 0, right: 0, top: 0, bottom: 0 };
                                                margin = breakpoint2.margin;
                                            }
                                            if (breakpoint2.padding)
                                            {
                                                padding = breakpoint2.padding;
                                            }
                                            else
                                            {
                                                breakpoint2.padding = { left: 0, right: 0, top: 0, bottom: 0 };
                                                padding = breakpoint2.padding;
                                            }

                                            if (breakpoint2.colOffsets)
                                            {
                                                offsets = breakpoint2.colOffsets;
                                            }
                                            else
                                            {
                                                breakpoint2.colOffsets = new Array(columns.length);
                                                offsets = breakpoint2.colOffsets;
                                            }
                                        }
                                    }
                                }
                            }
                            column.className = className;
                            var currentColumnWidth = columnWidth;
                            var columnsCount = 1;

                            if (padding)
                            {
                                if (undefined == padding.left) padding.left = 0;
                                if (undefined == padding.top) padding.top = 0;
                                if (undefined == padding.bottom) padding.bottom = 0;
                                if (undefined == padding.right) padding.right = 0;

                                currentColumnWidth -= padding.left;
                                currentColumnWidth -= padding.right;
                                column.style.paddingLeft = padding.left + "px";
                                column.style.paddingRight = padding.right + "px";
                                column.style.paddingTop = padding.top + "px";
                                column.style.paddingBottom = padding.bottom + "px";
                            }
                            column.style.marginLeft = "0px";
                            column.style.marginRight = "0px";
                            column.style.marginTop = "0px";
                            column.style.marginBottom = "0px";

                            if (margin)
                            {
                                if (undefined == margin.left) margin.left = 0;
                                if (undefined == margin.top) margin.top = 0;
                                if (undefined == margin.bottom) margin.bottom = 0;
                                if (undefined == margin.right) margin.right = 0;

                                currentColumnWidth -= margin.left;
                                currentColumnWidth -= margin.right;
                                column.style.marginRight = margin.right + "px";
                                column.style.marginLeft = margin.left + "px";
                                column.style.marginTop = margin.top + "px";
                                column.style.marginBottom = margin.bottom + "px";
                            }
                            left += currentColumnWidth;
                            if (left >= rowWidth - 1)
                            {
                                if (left - rowWidth <= 5)
                                {
                                    currentColumnWidth--;
                                    left = 0;
                                }
                            }
                            if (offsets && offsets[j] != undefined)
                            {
                                column.style.marginLeft = parseInt(column.style.marginLeft) + offsets[j] * rowWidth + "px";
                            }
                            column.style.width = currentColumnWidth + "px";
                        }
                    }
                    var visibility = columns.css('visibility');
                    columns.css('visibility', 'hidden');
                    updateColumns(rowWidth);
                    var newRowWidth = $(row).width();
                    if (rowWidth != newRowWidth)
                    {
                        updateColumns(newRowWidth);
                        $(window).trigger('resize');
                    }
                    columns.css('visibility', visibility);
                }
            }

            this.layouts.push({ object: layoutObject, resize: resize });
            if (this.layouts.length == 1)
            {
                var that = this;
                $(window).on("resize.response" + this._counter, function ()
                {
                    for (var i = 0; i < that.layouts.length; i++)
                    {
                        that.layouts[i].resize();
                    }
                });
                $(window).on("orientationchange.response" + this._counter, function ()
                {
                    for (var i = 0; i < that.layouts.length; i++)
                    {
                        that.layouts[i].resize();
                    }
                });
                $(window).on("orientationchanged.response" + this._counter, function ()
                {
                    for (var i = 0; i < that.layouts.length; i++)
                    {
                        that.layouts[i].resize();
                    }
                });
            }

            for (var i = 0; i < this.layouts.length; i++)
            {
                this.layouts[i].resize();
            }
        },

        destroy: function()
        {
            this.removeHandler($(window), 'resize.jqxresponse');
            this.removeHandler($(document), 'scroll.jqxresponse');
            if (this._counter > 0)
            {
                for (var i = 0 ; i < this._counter; i++)
                {
                    $(window).off("resize.response" + i);
                    $(window).off("orientationchange.response" + i);
                    $(window).off("orientationchanged.response" + i);
                }
            }

            for (var i = 0; i < this._handlers.length; i++) {
                var element = this._handlers[i];
                this.removeHandler($(element), 'mousedown.response' + element[0].id);
                this.removeHandler($(element), 'touchstart.response' + element[0].id);
                this.removeHandler($(element), 'mousemove.response' + element[0].id);
                this.removeHandler($(element), 'touchmove.response' + element[0].id);
                this.removeHandler($(element), 'mouseup.response' + element[0].id);
                this.removeHandler($(element), 'touchend.response' + element[0].id);
            }
        },

        resize: function (resizeFuncs) {
            var that = this;
            this.removeHandler($(window), 'resize.jqxresponse');
            this.addHandler($(window), 'resize.jqxresponse', function (event) {
                if (resizeFuncs) {
                    if ($.isArray(resizeFuncs)) {
                        for (var i = 0; i < resizeFuncs.length; i++) {
                            resizeFuncs[i]();
                        }
                    }
                    else {
                        resizeFuncs();
                    }
                }
                that.refreshSize();
            });
            if (resizeFuncs == null) {
                this.removeHandler($(window), 'resize.jqxresponse');
            }
        },

        pointerDown: function(element, func)
        {
            if (element && func) {
                var touchDevice = $.jqx.mobile.isTouchDevice();
                var that = this;
                var canCallFunc = true;

                if (touchDevice) {
                    var touchstart = $.jqx.mobile.getTouchEventName('touchstart') + '.response' + element[0].id;

                    if (func != null) {
                        this.addHandler($(element), touchstart, function (event) {
                            var position = $.jqx.position(event);
                            var result = func(event, position, "touch");
                            canCallFunc = false;
                            setTimeout(function () {
                                canCallFunc = true;
                            }, 500);
                            return result;
                        });
                    }
                    else {
                        this.removeHandler($(element), touchstart);
                    }
                }
                if (func != null) {
                    this.addHandler($(element), 'mousedown.response' + element[0].id, function (event) {
                        var position = $.jqx.position(event);
                        if (canCallFunc) {
                            return func(event, position, "mouse");
                        }
                    });
                } else {
                    this.removeHandler($(element), 'mousedown.response' + element[0].id);
                }
                this._handlers.push(element);
            }
        },

        pointerUp: function (element, func) {
            if (element) {
                var touchDevice = $.jqx.mobile.isTouchDevice();
                var that = this;
                var canCallFunc = true;

                if (touchDevice) {
                    var touchend = $.jqx.mobile.getTouchEventName('touchend') + '.response' + element[0].id;

                    if (func != null) {
                        this.addHandler($(element), touchend, function (event) {
                            var position = $.jqx.position(event);
                            var result = func(event, position, "touch");
                            canCallFunc = false;
                            setTimeout(function () {
                                canCallFunc = true;
                            }, 500);
                            return result;
                        });
                    }
                    else {
                        this.removeHandler($(element), touchend);
                     
                    }
                }
                if (func != null) {
                    this.addHandler($(element), 'mouseup.response' + element[0].id, function (event) {
                        var position = $.jqx.position(event);
                        if (canCallFunc) {
                            return func(event, position, "mouse");
                        }
                    });
                }
                else {
                    this.removeHandler($(element), 'mouseup.response' + element[0].id);
                }
                this._handlers.push(element);
            }
        },

        pointerMove: function (element, func) {
            if (element) {
                var touchDevice = $.jqx.mobile.isTouchDevice();
          
                if (touchDevice) {
                    var touchmove = $.jqx.mobile.getTouchEventName('touchmove') + '.response' + element[0].id;

                    if (func != null) {
                        this.addHandler($(element), touchmove, function (event) {
                            var touches = $.jqx.mobile.getTouches(event);
                            if (touches.length == 1) {
                                var position = $.jqx.position(event);
                                return func(event, position, "touch");
                            }
                        });
                    }
                    else {
                        this.removeHandler($(element), touchmove);
                    }
                }
                else {
                    if (func != null) {
                        this.addHandler($(element), 'mousemove.response' + element[0].id, function (event) {
                            var position = $.jqx.position(event);
                            return func(event, position, "mouse");
                        });
                    }
                    else {
                        this.removeHandler($(element), 'mousemove.response' + element[0].id);
                    }
                }
                this._handlers.push(element);
            }
        },

        isHidden: function(element)
        {
            return $.jqx.isHidden($(element));
        },

        inViewPort: function(element)
        {
            var viewPort = this.viewPort;

            if (element.getBoundingClientRect) {
                var r = element.getBoundingClientRect ? element.getBoundingClientRect() : {};
                return r && (r.bottom >= 0 && r.top <= viewPort.height && r.right >= 0 && r.left <= viewPort.width);
            }
            return false;
        },

        getScroll: function()
        {
            var obj = { left: window.pageXOffset || document.scrollLeft, top: window.pageYOffset || document.scrollTop };
            if (obj.left == undefined) obj.left = 0;
            if (obj.top == undefined) obj.top = 0;

            return obj;
        },

        getDocument: function()
        {
            return { width: $(document).width(), height: $(document).height() }
        },

        getViewPort: function ()
        {
            return { width: $(window).width(), height: $(window).height() }
        },

        getTouch: function()
        {
            var eventName = 'ontouchstart';
            var supported = (eventName in window);
            if (supported) {
                return true;
            }
            else {
                var eventName = 'MSPointerDown';
                var supported = (eventName in window);
                if (supported) {
                    return true;
                }
            }
            if ($.jqx.mobile.isWindowsPhone())
                return true;

            return false;
        },

        getDevice: function()
        {
            var osName = this.os.name;
            var match = window.location.search.match(/deviceType=(Tablet|Phone)/),
               nativeDeviceType = window.deviceType;
            var deviceType = "";
            if (match && match[1]) {
                deviceType = match[1];
            }
            else if (nativeDeviceType === 'iPhone') {
                deviceType = 'Phone';
            }
            else if (nativeDeviceType === 'iPad') {
                deviceType = 'Tablet';
            }
            else {
                if (osName != "Android" && osName != "iOS" && /Windows|Linux|MacOS|Mac OS|Mac OS X/.test(osName)) {
                    deviceType = 'Desktop';
                }
                else if (osName == "iOS" && navigator.userAgent.toLowerCase().indexOf('ipad') >= 0) {
                    deviceType = 'Tablet';
                }
                else if (osName == "RIMTablet") {
                    deviceType = 'Tablet';
                }
                else if (osName == "Android") {
                    if (this.os.version && this.os.version.substring(0,1).indexOf("3") >= 0) {
                        deviceType = 'Tablet';
                    }
                    else if (this.os.version && this.os.version.substring(0,1).indexOf("4") >= 0 && navigator.userAgent.search(/mobile/i) == -1) {
                        deviceType = 'Tablet';
                    }
                    else {
                        deviceType = 'Phone';
                    }
                    if (navigator.userAgent.toLowerCase().indexOf('kindle fire') >= 0) {
                        deviceType = 'Tablet';
                    }
                }
                else {
                    deviceType = 'Phone';
                }
            }

            if (/Windows/.test(osName)) {
                if (navigator.userAgent.indexOf('Windows Phone') >= 0 || navigator.userAgent.indexOf('WPDesktop') >= 0 || navigator.userAgent.indexOf('IEMobile') >= 0 || navigator.userAgent.indexOf('ZuneWP7') >= 0) {
                    deviceType = 'Phone';
                }
                else {
                    if (navigator.userAgent.indexOf('Touch') >= 0) {
                        deviceType = 'Tablet';
                        if (!this.getTouch()) {
                            deviceType = 'Desktop';
                        }
                    }
                }
            }

            return {
                // for device width - width, height for device height. The .availWidth/.availHeight methods give you the device size minus UI taskbars Device size is static and does not change when the page is resized or rotated.
                type: deviceType, touch: this.getTouch(), width: window.screen.width, height: window.screen.height,
                availWidth: window.screen.availWidth, availHeight: window.screen.availHeight
            }
        },

        canvas: function()
        {
            var canvasSupport = false;
            var canvas = document.createElement('canvas');
            if (canvas && canvas.getContext && canvas.getContext('2d')) {
                canvasSupport = true;
            }
            return canvasSupport;
        },

        vml: function () {
            if (this._vmlSupport == undefined) {
                var a = document.body.appendChild(document.createElement('div'));
                a.innerHTML = '<v:shape id="vml_flag1" adj="1" />';
                var b = a.firstChild;
                b.style.behavior = "url(#default#VML)";
                this._vmlSupport = b ? typeof b.adj == "object" : true;
                a.parentNode.removeChild(a);
            }
            return this._vmlSupport;
        },

        svg: function()
        {
            return document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#Image", "1.1"); 
        },

        getBrowser: function()
        {
            var ua = navigator.userAgent.toLowerCase();
            var name = "";
            var match = null;
            var that = this;

            browserNames = {
                msie: { name: 'Internet Explorer', eval: /(msie) ([\w.]+)/.exec(ua) },
                webkit: { name: 'Webkit', eval: /(webkit)[ \/]([\w.]+)/.exec(ua) },
                chrome: { name: 'Chrome', eval: /(chrome)[ \/]([\w.]+)/.exec(ua) },
                safari: { name: 'Safari', eval: /(safari)[ \/]([\w.]+)/.exec(ua) },
                edge: { name: 'Edge', eval: /Edge/i.exec(ua) },
                opera: { name: 'Opera', eval: /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) },
                operamobile: {name: 'Opera Mobile', eval: /(opera mobi)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(opera tablet)(?:.*version|)[ \/]([\w.]+)/.exec(ua)},
                dolphin: { name: 'Dolphin', eval: /(dolphin)[ \/]([\w.]+)/.exec(ua) },
                webosbrowser: {name: 'webOSBrowser', eval: /(wosbrowser)(?:.*version|)[ \/]([\w.]+)/.exec(ua)},
                chromemobile: {name: 'Chrome Mobile', eval: /(crmo)[ \/]([\w.]+)/.exec(ua)},
                silk: {name: 'Silk', eval: /(silk)[ \/]([\w.]+)/.exec(ua)},
                firefox: { name: 'Firefox', eval: /(firefox)[ \/]([\w.]+)/.exec(ua) },
                msie11: { name: 'Internet Explorer 11', eval: ua.indexOf("rv:11.0") >= 0 && ua.indexOf(".net4.0c") >= 0 },
                winphone: { name: 'Internet Explorer Mobile', eval: ua.indexOf("windows phone 8.1") >= 0},
                other: { name: 'Other', eval: ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) }
            }
            
            $.each(browserNames, function (index, value) {
                if (this.eval) {
                    if (this.name == "Other") {
                        if (!match) {
                            match = this.eval;
                            name = this.name;
                        }
                    }
                    else if (this.name == "Internet Explorer 11") {
                        if (!match) {
                            match = ["", "msie", 11.0];
                            name = "Internet Explorer"
                        }
                    }
                    else if (this.name == "Edge")
                    {
                        match = ["", "edge", ua.substring(ua.indexOf("edge") + 5)];
                        name = this.name;
                    }
                    else if (this.name == "Internet Explorer Mobile") {
                        if (!match) {
                            match = ["", "msie", 11.0];
                            name = "Internet Explorer"
                        }
                    }
                    else {
                        if (name == "Chrome" && this.name == "Safari")
                            return true;

                        match = this.eval;
                        name = this.name;
                    }
                }
            });

            if (match) {
                var browser = {
                    name: name,
                    accessName: match[1] || "",
                    version: match[2] || "0",
                    canvas: this.canvas(),
                    svg: this.svg(),
                    vml: this.vml()
                };

                browser[match[1]] = match[1];
            }
            else {
                browser = { name: 'Other', browser: 'other', version: '' };
            }

            return browser;
        },

        getOS: function () {
            var match = null;
            var version = "";
            var userAgent = navigator.userAgent;
            var os = "Other";
            var osTypes = {
                ios: { name: 'iOS', regex: new RegExp('(?:' + 'i(?:Pad|Phone|Pod)(?:.*)CPU(?: iPhone)? OS ' + ')([^\\s;]+)') },
                android: {name: 'Android', regex: new RegExp('(?:'+'(Android |HTC_|Silk/)'+')([^\\s;]+)')},
                webos: {name: 'webOS', regex: new RegExp('(?:'+'(?:webOS|hpwOS)\/'+')([^\\s;]+)')},
                blackberry: {name: 'BlackBerry', regex: new RegExp('(?:'+'BlackBerry(?:.*)Version\/'+')([^\\s;]+)')},
                rimTablet: {name: 'RIMTablet', regex:  new RegExp('(?:'+'RIM Tablet OS '+')([^\\s;]+)')},
                chrome: { name: 'Chrome OS', regex: new RegExp('CrOS') },
                mac: { name: 'MacOS', regex: new RegExp('mac') },
                win: {name: 'Windows', regex: new RegExp('win')},
                linux: { name: 'Linux', regex: new RegExp('linux') },
                bada: {name: 'Bada', regex: new RegExp('(?:'+'Bada\/'+')([^\\s;]+)')},
                other: {name: 'Other'}
            }

            var osys = "";
            var clientStrings = [
                { s: 'Windows 3.11', r: /Win16/ },
                { s: 'Windows 95', r: /(Windows 95|Win95|Windows_95)/ },
                { s: 'Windows ME', r: /(Win 9x 4.90|Windows ME)/ },
                { s: 'Windows 98', r: /(Windows 98|Win98)/ },
                { s: 'Windows CE', r: /Windows CE/ },
                { s: 'Windows 2000', r: /(Windows NT 5.0|Windows 2000)/ },
                { s: 'Windows XP', r: /(Windows NT 5.1|Windows XP)/ },
                { s: 'Windows Server 2003', r: /Windows NT 5.2/ },
                { s: 'Windows Vista', r: /Windows NT 6.0/ },
                { s: 'Windows 7', r: /(Windows 7|Windows NT 6.1)/ },
                { s: 'Windows 8.1', r: /(Windows 8.1|Windows NT 6.3)/ },
                { s: 'Windows 8', r: /(Windows 8|Windows NT 6.2)/ },
                { s: 'Windows 10', r: /(Windows 10|Windows NT 10)/ },
                { s: 'Windows NT 4.0', r: /(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/ },
                { s: 'Windows ME', r: /Windows ME/ },
                { s: 'Android', r: /Android/ },
                { s: 'Open BSD', r: /OpenBSD/ },
                { s: 'Sun OS', r: /SunOS/ },
                { s: 'Linux', r: /(Linux|X11)/ },
                { s: 'BB10', r: /BB10/},
                { s: 'MeeGo', r: /MeeGo/},
                { s: 'iOS', r: /(iPhone|iPad|iPod)/ },
                { s: 'Mac OS X', r: /Mac OS X/ },
                { s: 'Mac OS', r: /(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/ },
                { s: 'QNX', r: /QNX/ },
                { s: 'UNIX', r: /UNIX/ },
                { s: 'BeOS', r: /BeOS/ },
                { s: 'OS/2', r: /OS\/2/ },
                { s: 'Search Bot', r: /(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver)/ }
            ];
            for (var id in clientStrings) {
                var cs = clientStrings[id];
                if (cs.r.test(userAgent)) {
                    osys = cs.s;
                    break;
                }
            }

            var osVersion = "";

            if (/Windows/.test(osys)) {
                osVersion = /Windows (.*)/.exec(osys)[1];
                osys = 'Windows';
            }
            if (/BB10/.test(osys)) {
                osVersion = '10';
                osys = 'BlackBerry';
            }
            switch (os) {
                case 'Mac OS X':
                    osVersion = /Mac OS X (10[\.\_\d]+)/.exec(userAgent)[1];
                    break;

                case 'Android':
                    osVersion = /Android ([\.\_\d]+)/.exec(userAgent)[1];
                    break;

                case 'iOS':
                    osVersion = /OS (\d+)_(\d+)_?(\d+)?/.exec(nVer);
                    osVersion = osVersion[1] + '.' + osVersion[2] + '.' + (osVersion[3] | 0);
                    break;

            }
            if (osVersion != "") version = osVersion;

            $.each(osTypes, function (index, value) {
                match = userAgent.match(this.regex) || userAgent.toLowerCase().match(this.regex);

                if (match) {                    
                    if (!this.name.match(/Windows|Linux|MacOS/))
                    {
                        if (match[1] && (match[1] == "HTC_" || match[1] == "Silk/")) {
                            version = "2.3";
                        } else {
                            version = match[match.length - 1];
                        }
                    }

                    os = { name: this.name, version: version, platform: navigator.platform };
                    return false;
                }
            });

            if (os && os.name == "Other") os.name = osys;
            if (os && os.name != "" && osys != "") os.name = osys;
            if (os && os.version == "" && osVersion != "") os.version = osVersion;
            return os;
        }
    }
})(jqxBaseFramework);
