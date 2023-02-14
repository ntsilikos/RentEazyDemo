let tenantformOpen = false;
const openTenantFormButton = document.getElementById('open-form-button');
const tenantForm = document.getElementById('tenant-form');



openTenantFormButton.addEventListener('click', () => {
  if (tenantformOpen == false) {
    tenantformOpen = true;
    tenantForm.style.display = 'block';
  } else {
    tenantformOpen = false;
    tenantForm.style.display = 'none';
  }
})




const TenantFormSubmitStatus = document.getElementById('status-response-tenant-form');

function getTenants() {
  fetch('/tenants')
    .then(response => response.json())
    .then(tenants => {
      displayTenants(tenants);
    })
    .catch(error => {
      console.error(error);
    });
}

getTenants();

tenantForm.addEventListener('submit', (event) => {
  event.preventDefault();
  const tenantName = document.getElementById('tenantName').value;
  const tenantPhone = document.getElementById('tenantPhone').value;
  const monthlyPayment = document.getElementById('monthlyPayment').value;
  const currentRentOwed = document.getElementById('currentRentOwed').value;
  const hasPet = document.getElementById('hasPet').checked;
  const moveInDate = document.getElementById('moveInDate').value;
  const securityDeposit = document.getElementById('securityDeposit').value
  const tenant = { tenantName, tenantPhone, monthlyPayment, currentRentOwed, hasPet, moveInDate, securityDeposit };
  fetch('/tenants', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(tenant),
  })
    .then((response) => {
      if (response.status >= 200 && response.status < 300) {
        // Transaction was created successfully
        console.log('Transaction created');
        TenantFormSubmitStatus.innerHTML = 'Tenant created! :)'
        setInterval(function () { TenantFormSubmitStatus.innerHTML = '' }, 3000);
        return response.json();
      } else {
        // There was an error creating the transaction
        console.error('Error creating transaction');
        TenantFormSubmitStatus.innerHTML = 'Error creating tenant! :('
        setInterval(function () { TenantFormSubmitStatus.innerHTML = '' }, 3000);
        throw new Error(response.statusText);
      }
    })
    .then((data) => {
      console.log(data);
      getTenants();
    });
});


const tenantTable = document.getElementById('list-of-tenants');

function displayTenants(tenants) {
  // Clear the current table
  tenantTable.innerHTML = '';

  // Create table headers
  let tableHeaders = `<tr> <th>Tenant Name</th> <th>Tenant Phone</th> <th>Monthly Payment</th> <th>Security Deposit</th> <th>Address</th> <th>Unit Number</th> <th>Actions</th> </tr>`;
  tenantTable.innerHTML = tableHeaders;

  // Loop through the tenants and add a row for each one
  tenants.forEach(tenant => {
    let newRow = `<tr> <td>${tenant.tenantName}</td> <td>${tenant.tenantPhone}</td> <td>${tenant.monthlyPayment}</td> <td>${tenant.securityDeposit}</td> <td>${tenant.building}</td> <td>${tenant.unitNumber}</td> <td><button class="delete-button" data-tenant-name="${tenant.tenantName}">Delete</button></td> </tr>`;
    tenantTable.innerHTML += newRow;
  });

  // Add event listener for delete buttons
  const deleteButtons = document.querySelectorAll('.delete-button');
  deleteButtons.forEach(button => {
    button.addEventListener('click', event => {
      let tenantName = event.target.getAttribute("data-tenant-name")
      deleteTenant(tenantName);
    });
  });
}

function deleteTenant(tenantName) {
  if (confirm("Are you sure you want to delete " + tenantName + "?")) {
    // send delete request to the server and delete the tenant
    fetch('/tenants/' + tenantName, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300) {
          console.log("Tenant deleted");
          getTenants();
        } else {
          console.error("Error deleting tenant");
        }
      });
  }
}



const transactionForm = document.getElementById('transaction-form');
const transactionFormSubmitStatus = document.getElementById('status-response-transaction-form');

transactionForm.addEventListener('submit', (event) => {
  event.preventDefault();
  const tenantName = document.getElementById('tenantNameForTransaction').value;
  const amount = document.getElementById('amountPaid').value;
  const forCurrentMonth = document.getElementById('forCurrentMonth').checked;
  const paymentDate = document.getElementById('paidDate').value;
  const transaction = { tenantName, amount, forCurrentMonth, paymentDate };
  fetch('/transactions/' + tenantName, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(transaction),
  })
    .then((response) => {
      if (response.status >= 200 && response.status < 300) {
        // Transaction was created successfully
        console.log('Transaction created');
        transactionFormSubmitStatus.innerHTML = 'Transaction created! :)'
        setInterval(function () { transactionFormSubmitStatus.innerHTML = '' }, 3000);
        document.getElementById('tenantNameForTransaction').innerText = '';
        return response.json();
      } else {
        // There was an error creating the transaction
        console.error('Error creating transaction');
        transactionFormSubmitStatus.innerHTML = 'Error creating transaction! :('
        setInterval(function () { transactionFormSubmitStatus.innerHTML = '' }, 3000);
        throw new Error(response.statusText);
      }
    })
    .then((data) => {
      console.log(data);
      // getTransactions();  // you can call this function if you want to update the list of transactions after the new transaction is added
    });
});

const transactionTable = document.getElementById('list-of-transactions');

function getTransactions() {
  fetch('/transactions')
    .then(response => response.json())
    .then(transactions => {
      displayTransactions(transactions);
    })
    .catch(error => {
      console.error(error);
    });
}

function displayTransactions(transactions) {
  // Clear the current table
  transactionTable.innerHTML = '';

  // Create table headers
  let tableHeaders = `<tr> <th>Tenant Name</th> <th>Payment Date</th> <th>Amount</th> <th>For Current Month</th> </tr>`;
  transactionTable.innerHTML = tableHeaders;

  // Loop through the transactions and add a row for each one
  transactions.forEach(transaction => {
    let newRow = `<tr> <td>${transaction.transactionId}</td> <td>${transaction.paymentDate}</td> <td>${transaction.amount}</td> <td>${transaction.forCurrentMonth}</td> </tr>`;
    transactionTable.innerHTML += newRow;
  });
}

const openTenantTableButton = document.getElementById('open-tenant-list-button');
let tenantTableOpen = false;

openTenantTableButton.addEventListener('click', () => {
  if (tenantTableOpen == false) {
    tenantTableOpen = true;
    tenantTable.style.display = null;
  } else if (tenantTableOpen == true) {
    tenantTableOpen = false;
    tenantTable.style.display = 'none';
  }
});

const openTransactionTableButton = document.getElementById('open-transactions-button');
let transactionTableOpen = false;

openTransactionTableButton.addEventListener('click', () => {
  if (transactionTableOpen == false) {
    transactionTableOpen = true;
    transactionTable.style.display = null;
  } else if (transactionTableOpen == true) {
    transactionTableOpen = false;
    transactionTable.style.display = 'none';
  }
});

let transactionformOpen = false;
const openTransactionFormButton = document.getElementById('open-transaction-form');

openTransactionFormButton.addEventListener('click', () => {
if(transactionformOpen == false) {
transactionformOpen = true;
transactionForm.style.display = 'block';
} else {
transactionformOpen = false;
transactionForm.style.display = 'none';
}
});

getTransactions()
  .then(transactions => {
    displayTenants(transactions);
  })

getTenants()
  .then(tenants => {
    displayTenants(tenants);
  })
  // Make sure the tenants variable is defined and contains the correct data before calling the displayTenants function

