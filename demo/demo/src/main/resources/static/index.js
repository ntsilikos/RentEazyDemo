const form = document.getElementById('tenant-form');

const openFormButton = document.getElementById('open-form-button');
openFormButton.addEventListener('click', () => {
  form.style.display = 'block';
  openFormButton.style.display = 'none';
  openListButton.style.display = 'none';
});

const closeFormButton = document.getElementById('close-form-button');
closeFormButton.addEventListener('click', () => {
  form.style.display = 'none';
  openFormButton.style.display = 'block';
  openListButton.style.display = 'block';
});

const openListButton = document.getElementById('open-list-button');

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
    });
});


let listFilled = false;

const button = document.getElementById('test-button');
openListButton.addEventListener('click', () => {
  if (!listFilled) {
    fetch('/tenants')
      .then(response => response.json())
      .then(tenants => {
        const list = document.getElementById('tenant-list');
        for (const tenant of tenants) {
          const item = document.createElement('li');
          item.textContent = tenant.tenantName;
          list.appendChild(item);
        }
        listFilled = true;
      });
  }
});