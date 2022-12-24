let formOpen = false;
const openFormButton = document.getElementById('open-form-button');
const formAddTenant = document.getElementById('tenant-form');
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

//transaction-form
const transactionForm = document.getElementById('transaction-form');

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


formAddTenant.addEventListener('submit', (event) => {
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


formAddTenant.addEventListern('submit', (event) =>{
  event.preventDefault();
  const 
})












const deleteButtons = document.getElementsByClassName('delete-button');
for (let i = 0; i < deleteButtons.length; i++) {
  const button = deleteButtons[i];
  button.addEventListener('click', () => {
    // Get the tenant name from the button's data-id attribute
    const tenantName = button.getAttribute('data-id');
    // Make a DELETE request to the '/tenants' endpoint with the tenant name
    fetch(`/tenants?name=${tenantName}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        getTenants();
      });
  });
}


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


      // Add event listeners to the edit buttons
      const editButtons = document.getElementsByClassName('edit-button');
      for (let i = 0; i < editButtons.length; i++) {
        const editButton = editButtons[i];
        editButton.addEventListener('click', () => {
          // Get the row for the tenant
          const row = editButton.parentNode.parentNode;
          // Get the cells in the row
          const cells = row.getElementsByTagName('td');

          // Turn the cells into input fields
          for (let j = 0; j < cells.length; j++) {
            const cell = cells[j];
                  if (cell.textContent !== 'Edit' && cell.textContent !== 'Delete') {
                    // Set the cell's innerHTML to an input field with the cell's current value
                    cell.innerHTML = `<input type="text" value="${cell.textContent}">`;
                  }
                
          }

          // Add a "done" button to the row
          const doneButton = document.createElement('button');
          doneButton.textContent = 'Done';
          const buttonCell = row.insertCell();
          buttonCell.appendChild(doneButton);

          // Add an event listener to the "done" button
          doneButton.addEventListener('click', () => {
            // Get the updated values for the tenant's name, phone, monthly payment, current rent owed, hasPet, and move-in date from the input fields
            const updatedTenant = {
                         tenantName: cells[0].firstChild.value,
                          tenantPhone: cells[1].firstChild.value,
                          monthlyPayment: cells[2].firstChild.value,
                          currentRentOwed: cells[3].firstChild.value,
                          hasPet: cells[4].firstChild.value === 'Yes',
                          moveInDate: cells[5].firstChild.value
                        };

                        // Make a PUT request to the '/tenants' endpoint with the updated tenant data
                        fetch('/tenants', {
                          method: 'PUT',
                          headers: {
                            'Content-Type': 'application/json',
                          },
                          body: JSON.stringify(updatedTenant),
                        })
                          .then((response) => response.json())
                          .then((data) => {
                            console.log(data);
                            // Update the table to reflect the changes
                            for (let j = 0; j < cells.length; j++) {
                              const cell = cells[j];
                              // Set the cell's innerHTML back to the updated tenant data
                              cell.innerHTML = `
                                ${Object.values(updatedTenant)[j]}
                                <button class="delete-button" data-id="${updatedTenant.tenantName}">Delete</button>
                                <button class="edit-button" data-id="${updatedTenant.tenantName}">Edit</button>
                              `;
                            }
                            // Remove the "done" button
                            row.deleteCell(-1);
                          });
                      });

        });
      }
    });
}