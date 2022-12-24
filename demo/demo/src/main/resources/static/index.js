let formOpen = false;
const openFormButton = document.getElementById('open-form-button');
const form = document.getElementById('tenant-form');
openFormButton.addEventListener('click', () => {
  // Call the getTenants function
  if(formOpen == false) {
        form.style.display = 'block';
        openFormButton.innerHTML = "Close Form"
        formOpen = true;
  }
  else {
        openFormButton.innerHTML = "Add Tenant"
        formOpen = false;
        form.style.display = 'none'
  }
});



let tableOpen = false;
const viewTenantsButton = document.getElementById('open-list-button');
let tenantTable = document.getElementById('list-of-tenants');
viewTenantsButton.addEventListener('click', () => {
  // Call the getTenants function
  if(tableOpen == false) {
        tenantTable.style.display = 'block'
        viewTenantsButton.innerHTML = "Close Tenants Table"
        getTenants();
        tableOpen = true;

  }
  else {
        viewTenantsButton.innerHTML = "Open Tenants Table"
        tableOpen = false;
        tenantTable.style.display = 'none'
  }
});

//open-transaction-form
let transactionFormOpen = false;
const openTransactionFormButton = document.getElementById('open-transaction-form');
const transactionForm = document.getElementById('transaction-form');
openTransactionFormButton.addEventListener('click', () => {
  // Call the getTenants function
  if(transactionFormOpen == false) {
        transactionForm.style.display = 'block';
        openTransactionFormButton.innerHTML = "Close Form"
        transactionFormOpen = true;
  }
  else {
        openTransactionFormButton.innerHTML = "Add Transaction"
        transactionFormOpen = false;
        transactionForm.style.display = 'none'
  }
});


form.addEventListener('submit', (event) => {
  event.preventDefault();
  const tenantName = document.getElementById('tenantName').value;
  const tenantPhone = document.getElementById('tenantPhone').value;
  const monthlyPayment = document.getElementById('monthlyPayment').value;
  const currentRentOwed = document.getElementById('currentRentOwed').value;
  const hasPet = document.getElementById('hasPet').checked;
  const moveInDate = document.getElementById('moveInDate').value;
  const tenant = { tenantName, tenantPhone, monthlyPayment, currentRentOwed, hasPet, moveInDate };
  fetch('/tenants', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(tenant),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      getTenants();
    });
});


function getTenants(name) {
  // Make a GET request to the endpoint that returns the list of tenants
  fetch(`/tenants${name ? `?name=${name}` : ''}`)
    .then(response => response.json()) // Parse the response as JSON
    .then(tenants => {
      // Get the table element
      const table = document.getElementById('list-of-tenants');

      // Clear the table
      table.innerHTML = '';

      // Set the table headers
      table.innerHTML = `
        <tr>
          <th>Name</th>
          <th>Phone</th>
          <th>Monthly Payment</th>
          <th>Current Rent Owed</th>
          <th>Has Pet</th>
          <th>Move-in Date</th>
        </tr>
      `;

      // Add a row for each tenant
      tenants.forEach(tenant => {
        table.innerHTML += `
          <tr>
            <td>${tenant.tenantName}</td>
            <td>${tenant.tenantPhone}</td>
            <td>${tenant.monthlyPayment}</td>
            <td>${tenant.currentRentOwed}</td>
            <td>${tenant.hasPet ? 'Yes' : 'No'}</td>
            <td>${tenant.moveInDate}</td>
            <td><button class="delete-button" data-id="${tenant.tenantName}">Delete</button></td>
            <td><button class="edit-button" data-id="${tenant.tenantName}">Edit</button></td>
          </tr>
        `;
      });

      // Add event listeners to the delete buttons
      const deleteButtons = document.getElementsByClassName('delete-button');
      for (const button of deleteButtons) {
        button.addEventListener('click', (event) => {
          const tenantName = event.target.getAttribute('data-id');
          fetch(`/tenants/${tenantName}`, {
            method: 'DELETE',
          }).then(() => {
            // Refresh the table after deleting a tenant
            getTenants();
          });
        });
      }
    });
}


transactionForm.addEventListener('submit', (event) => {
  event.preventDefault();

  const tenantName = document.getElementById('tenantNameForTransaction').value;
  const amountPaid = document.getElementById('amountPaid').value;
  const paidDate = document.getElementById('paidDate').value;

  const transaction = {
    amount: amountPaid,
    paymentDate: paidDate
  };

  fetch('/transactions/' + tenantName, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(transaction)
  }).then(response => {
    if (response.ok) {
      // Transaction was created successfully
      console.log('Transaction created');
    } else {
      // There was an error creating the transaction
      console.error('Error creating transaction');
    }
  });
});
