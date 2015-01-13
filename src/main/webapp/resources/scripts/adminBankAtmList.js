/**
 * Created by Olavin on 13.01.2015.
 */

$(document).ready(function () {
    $("#atmsgrid").dataTable({
        serverSide: true,
        pageLength: 20,
        ajax: {
            url: "/getBankATMs",
            data: function ( data ) {
                planify(data);
                data.bankId = $("#bankid").val();
            }
        },
        columnDefs: [
            {
                render: function ( data, type, row ) {
                    if ( type === 'display' ) {
                        return '<input type="checkbox" class="select" value="'+data+'">';
                    }
                    return data;
                },
                className: "dt-body-center",
                width: "25px",
                targets: 0
            },
            {
                orderable: false,
                targets: [ 0, 5 ]
            }
        ],
        order: [ 1, 'asc' ],
        orderMulti: false,
        processing: true
    });
});

function planify(data) {
    for (var i = 0; i < data.columns.length; i++) {
        column = data.columns[i];
        column.searchRegex = column.search.regex;
        column.searchValue = column.search.value;
        delete(column.search);
    }
}
