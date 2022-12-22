const form = document.getElementById('tenant-form');
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
