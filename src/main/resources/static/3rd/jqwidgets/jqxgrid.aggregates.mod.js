/*
jQWidgets v4.1.0 (2016-Mar)
Copyright (c) 2011-2016 jQWidgets.
License: http://jqwidgets.com/license/
*/

(function ($) {
    $.extend($.jqx._jqxGrid.prototype, {

        _updatecolumnaggregates: function (column, aggregates, columnelement) {
            var me = this;
            if (!aggregates) {
                columnelement.children().remove();
                columnelement.html('');
                if (column.aggregatesrenderer) {
                    var obj = {};
                    if (column.aggregates) {
                        obj = this.getcolumnaggregateddata(column.datafield, column.aggregates);
                    }

                    var renderstring = column.aggregatesrenderer({}, column, columnelement, null);
                    columnelement.html(renderstring);
                }
                return;
            }

            columnelement.children().remove();
            columnelement.html('');
            if (column.aggregatesrenderer) {
                if (aggregates) {
                    var renderstring = column.aggregatesrenderer(aggregates[column.datafield], column, columnelement, this.getcolumnaggregateddata(column.datafield, aggregates[column.datafield]));
                    columnelement.html(renderstring);
                }
            }
            else {
                $.each(aggregates, function () {
                    var aggregate = this;
                    for (obj in aggregate) {
                        var field = $('<div style="position: relative; margin: 4px; overflow: hidden;"></div>');
                        var name = obj;
                        name = me._getaggregatename(name);
						if(!name) field.html(aggregate[obj]);
						else field.html(name + ':' + aggregate[obj]);
                        if (me.rtl) {
                            field.addClass(me.toThemeProperty('jqx-rtl'));
                        }

                        columnelement.append(field);
                    }
                });
            }
        }
    });
})(jqxBaseFramework);


