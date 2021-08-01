var getLocalization = function (culture) {
    var localization = {
		// separator of parts of a date (e.g. '/' in 11/05/1955)
		'/': "-",
		// separator of parts of a time (e.g. ':' in 05:44 PM)
		':': ":",
		// the first day of the week (0 = Sunday, 1 = Monday, etc)
		firstDay: 0,
		days: {
			// full day names
			names: ["일","월","화","수","목","금","토"],
			// abbreviated day names
			namesAbbr: ["일","월","화","수","목","금","토"],
			// shortest day names
			namesShort: ["일","월","화","수","목","금","토"]
		},
		months: {
			// full month names (13 months for lunar calendards -- 13th month should be "" if not lunar)
			names: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월",""],
			// abbreviated month names
			namesAbbr: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월",""]
		},
		// AM and PM designators in one of these forms:
		// The usual view, and the upper and lower case versions
		//      [standard,lowercase,uppercase]
		// The culture does not use AM or PM (likely all standard date formats use 24 hour time)
		//      null
		AM: ["오전","오전","오전"],
		PM: ["오후","오후","오후"],
		eras: [
			// eras in reverse chronological order.
			// name: the name of the era in this culture (e.g. A.D., C.E.)
			// start: when the era starts in ticks (gregorian, gmt), null if it is the earliest supported era.
			// offset: offset in years from gregorian calendar
            [{"name":"단기","start":null,"offset":-2333}]
		],
		twoDigitYearMax: 4362,
		patterns: {
			// short date pattern
			d: "yyyy-MM-dd",
			// long date pattern
			D: "yyyy'년' M'월' d'일'",
			// short time pattern
			t: "h:mm",
			// long time pattern
			T: "h:mm:ss",
			// long date, short time pattern
			f: "yyyy'년' M'월 'd'일' H:mm",
			// long date, long time pattern
			F: "yyyy'년 'M'월 'd'일' H:mm:ss",
			// month/day pattern
			M: "M'월 'd'일'",
			// month/year pattern
			Y: "yyyy'년 'M'월'",
			// S is a sortable format that does not vary by culture
			S: "yyyy\u0027-\u0027MM\u0027-\u0027dd\u0027T\u0027HH\u0027:\u0027mm\u0027:\u0027ss",
			// formatting of dates in MySQL DataBases
			ISO: "yyyy-MM-dd hh:mm:ss",
			ISO2: "yyyy-MM-dd HH:mm:ss",
			d1: "dd.MM.yyyy",
			d2: "dd-MM-yyyy",
			d3: "dd-MMMM-yyyy",
			d4: "dd-MM-yy",
			d5: "H:mm",
			d6: "HH:mm",
			d7: "HH:mm tt",
			d8: "dd/MMMM/yyyy",
			d9: "MMMM-dd",
			d10: "MM-dd",
			d11: "MM-dd-yyyy"
		},
		percentsymbol: "%",
		currencysymbol: "\\",
		currencysymbolposition: "before",
		decimalseparator: '.',
		thousandsseparator: ',',
		pagergotopagestring: "페이지 이동:",
		pagershowrowsstring: "목록 수:",
		pagerrangestring: " of ",
		pagerpreviousbuttonstring: "previous",
		pagernextbuttonstring: "Next",
		pagerfirstbuttonstring: "First",
		pagerlastbuttonstring: "Last",
		groupsheaderstring: "Drag a column and drop it here to group by that column",
		sortascendingstring: "Ascending order",
		sortdescendingstring: "Descending order",
		sortremovestring: "Remove order",
		groupbystring: "Group By this column",
		groupremovestring: "Remove from groups",
		filterclearstring: "초기화",
		filterstring: "조건",
		filtershowrowstring: "Show rows where:",
		filterorconditionstring: "Or",
		filterandconditionstring: "And",
		filterselectallstring: "(Select All)",
		filterchoosestring: "Please Choose:",
		filterstringcomparisonoperators: ['empty', 'not empty', 'enthalten', 'enthalten(match case)',
			'does not contain', 'does not contain(match case)', 'starts with', 'starts with(match case)',
			'ends with', 'ends with(match case)', 'equal', 'equal(match case)', 'null', 'not null'],
		filternumericcomparisonoperators: ['equal', 'not equal', 'less than', 'less than or equal', 'greater than', 'greater than or equal', 'null', 'not null'],
		filterdatecomparisonoperators: ['equal', 'not equal', 'less than', 'less than or equal', 'greater than', 'greater than or equal', 'null', 'not null'],
		filterbooleancomparisonoperators: ['equal', 'not equal'],
		validationstring: "Entered value is not valid",
		emptydatastring: "No data",
		filterselectstring: "Select Filter",
		loadtext: "Loading...",
		clearstring: "Clear",
		todaystring: "Today"
	};

    return localization;
}
