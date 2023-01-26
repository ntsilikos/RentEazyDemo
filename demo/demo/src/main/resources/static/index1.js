function submitTenantForm() {
  var tenantName = document.getElementById("tenantName").value;
  var tenantPhone = document.getElementById("tenantPhone").value;
  var monthlyPayment = document.getElementById("monthlyPayment").value;
  var currentRentOwed = document.getElementById("currentRentOwed").value;
  var hasPet = document.getElementById("hasPet").checked;
  var moveInDate = document.getElementById("moveInDate").value;
  var securityDeposit = document.getElementById("securityDeposit").value;
  var currentMonthPaid = document.getElementById("currentMonthPaid").checked;

  // Send a POST request to the server with the tenant data
  fetch("/tenants", {
    method: "POST",
    headers: {
      "Content-Type":"application/json"
    },
    body: JSON.stringify({
    tenantName: tenantName,
    tenantPhone: tenantPhone,
    monthlyPayment: monthlyPayment,
    currentRentOwed: currentRentOwed,
    hasPet: hasPet,
    moveInDate: moveInDate,
    securityDeposit: securityDeposit,
    currentMonthPaid: currentMonthPaid
    })
    })
    .then(response => response.json())
    .then(data => {
    if(data) {
    alert("Tenant added successfully!");
    // Clear the form fields
    document.getElementById("tenantName").value = "";
    document.getElementById("tenantPhone").value = "";
    document.getElementById("monthlyPayment").value = "";
    document.getElementById("currentRentOwed").value = "";
    document.getElementById("hasPet").checked = false;
    document.getElementById("moveInDate").value = "";
    document.getElementById("securityDeposit").value = "";
    document.getElementById("currentMonthPaid").checked = false;
    } else {
    alert("Error adding tenant, please try again.");
    }
    });
    }

    function submitTransactionForm() {
      var tenantName = document.getElementById("tenantName").value;
      var paymentDate = document.getElementById("paymentDate").value;
      var amount = document.getElementById("amount").value;
      var forCurrentMonth = document.getElementById("forCurrentMonth").checked;
      var isLatePayment = document.getElementById("isLatePayment").checked;
    
      // Send a POST request to the server with the transaction data
      fetch(`/transactions/${tenantName}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          paymentDate: paymentDate,
          amount: amount,
          forCurrentMonth: forCurrentMonth,
          isLatePayment: isLatePayment
        })
      })
      .then(response => response.json())
      .then(data => {
        if(data) {
          alert("Transaction added successfully!");
          // Clear the form fields
          document.getElementById("tenantName").value = "";
          document.getElementById("paymentDate").value = "";
          document.getElementById("amount").value = "";
          document.getElementById("forCurrentMonth").checked = false;
          document.getElementById("isLatePayment").checked = false;
        } else {
          alert("Error adding transaction, please try again.");
        }
      });
    }

    function getTenantList() {
      fetch("/tenants")
        .then(response => response.json())
        .then(tenants => {      // Clear the current tenant table
          document.getElementById("tenantTableBody").innerHTML = "";
    
          // Create a new row for each tenant in the list
          for(var i = 0; i < tenants.length; i++) {
            var tenant = tenants[i];
            var row = document.createElement("tr");
    
            var nameCell = document.createElement("td");
            nameCell.innerHTML = tenant.tenantName;
            row.appendChild(nameCell);
    
            var phoneCell = document.createElement("td");
            phoneCell.innerHTML = tenant.tenantPhone;
            row.appendChild(phoneCell);
    
            var monthlyPaymentCell = document.createElement("td");
            monthlyPaymentCell.innerHTML = tenant.monthlyPayment;
            row.appendChild(monthlyPaymentCell);
    
            var currentRentOwedCell = document.createElement("td");
            currentRentOwedCell.innerHTML = tenant.currentRentOwed;
            row.appendChild(currentRentOwedCell);
    
            var hasPetCell = document.createElement("td");
            hasPetCell.innerHTML = tenant.hasPet;
            row.appendChild(hasPetCell);
    
            var moveInDateCell = document.createElement("td");
            moveInDateCell.innerHTML = tenant.moveInDate;
            row.appendChild(moveInDateCell);
    
            var securityDepositCell = document.createElement("td");
            securityDepositCell.innerHTML = tenant.securityDeposit;
            row.appendChild(securityDepositCell);
    
            var currentMonthPaidCell = document.createElement("td");
            currentMonthPaidCell.innerHTML = tenant.currentMonthPaid;
            row.appendChild(currentMonthPaidCell);
    
            document.getElementById("tenantTableBody").appendChild(row);
          }
        });
    }

    
function getTransactionList() {
  fetch("/transactions")
    .then(response => response.json())
    .then(transactions => {
      // Clear the current transaction table
      document.getElementById("transactionTableBody").innerHTML = "";  // Create a new row for each transaction in the list
      for(var i = 0; i < transactions.length; i++) {
        var transaction = transactions[i];
        var row = document.createElement("tr");
    
        var nameCell = document.createElement("td");
        nameCell.innerHTML = transaction.tenantName;
        row.appendChild(nameCell);
    
        var paymentDateCell = document.createElement("td");
        paymentDateCell.innerHTML = transaction.paymentDate;
        row.appendChild(paymentDateCell);
    
        var amountCell = document.createElement("td");
        amountCell.innerHTML = transaction.amount;
        row.appendChild(amountCell);
    
        var forCurrentMonthCell = document.createElement("td");
        forCurrentMonthCell.innerHTML = transaction.forCurrentMonth;
        row.appendChild(forCurrentMonthCell);
    
        var isLatePaymentCell = document.createElement("td");
        isLatePaymentCell.innerHTML = transaction.isLatePayment;
        row.appendChild(isLatePaymentCell);
    
        document.getElementById("transactionTableBody").appendChild(row);
      }
    });
  }