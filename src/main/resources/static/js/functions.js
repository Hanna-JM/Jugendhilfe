
function furtherEducationSearchFunction() {

      // Deklarieren der Variablen
      var input, filter, table, tr, i, j, column_length, count_td;
      column_length = document.getElementById('listFortbildungTabelle').rows[0].cells.length;
      input = document.getElementById("furthereducationsearchtable");
      filter = input.value.toLowerCase();
      table = document.getElementById("listFortbildungTabelle");
      tr = table.getElementsByTagName("tr");

        // Schleife durch alle Zeilen der Tabelle und blendet alle aus, die nicht zur such-query passen
        for (i = 1; i < tr.length; i++) { // i=1 außer erster zeile (tbl header)
        count_td = 0;
        for(j = 0; j < column_length; j++){ // i=0 mit erster spalte
            td = tr[i].getElementsByTagName("td")[j];
            if (td) {
              if ( td.innerHTML.toLowerCase().indexOf(filter) > -1)  {
                count_td++;
              }
            }
        }
        if(count_td > 0){
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
      }

 }



function youthWelfareOfficeSearchFunction() {

  var input, filter, table, tr, i, j, column_length, count_td;
      column_length = document.getElementById('listBehoerdenTabelle').rows[0].cells.length;
      input = document.getElementById("youthwelfareofficesearchtable");
      filter = input.value.toLowerCase();
      table = document.getElementById("listBehoerdenTabelle");
      tr = table.getElementsByTagName("tr");

        // Schleife durch alle Zeilen der Tabelle und blendet alle aus, die nicht zur such-query passen
        for (i = 1; i < tr.length; i++) { // außer erster (heading) zeile
        count_td = 0;
        for(j = 1; j < column_length; j++){ // außer erster spalte, _length -1 gibt zugang zu letztem element
            td = tr[i].getElementsByTagName("td")[j];     /* hier spalten hinzufügen, auf die der filter angewendet werden soll */
            if (td) {
              if ( td.innerHTML.toLowerCase().indexOf(filter) > -1)  {
                count_td++;
              }
            }
        }
        if(count_td > 0){
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
      }

 }
