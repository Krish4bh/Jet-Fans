const loginTab = document.querySelector("#login-tab");
const registerTab = document.querySelector("#register-tab");
const loginContent = document.querySelector("#login-content");
const registerContent = document.querySelector("#register-content");

loginTab.addEventListener("click", () => {
  loginTab.classList.add("active");
  registerTab.classList.remove("active");

  loginContent.classList.remove("d-none");
  registerContent.classList.add("d-none");
});

registerTab.addEventListener("click", () => {
  registerTab.classList.add("active");
  loginTab.classList.remove("active");

  loginContent.classList.add("d-none");
  registerContent.classList.remove("d-none");
});

//------------------------------------------------------------

// Login Submit Event

const form = document.querySelector("#login-form");
const loginEmailInput = document.querySelector("#lg-email-input");
const loginPassword = document.querySelector("#lg-password-input");
const lgEmailErrorMsg = document.querySelector("#lg-email-error");
const lgPasswordErrorMsg = document.querySelector("#lg-password-error");

form.addEventListener("submit", (e) => {
  lgEmailErrorMsg.classList.add("d-none");
  lgPasswordErrorMsg.classList.add("d-none");

  const email = loginEmailInput.value.trim();
  const password = loginPassword.value.trim();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  let isValid = true;

  if (!emailRegex.test(email)) {
    lgEmailErrorMsg.textContent = "Enter a valid email";
    lgEmailErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!passRegex.test(password)) {
    lgPasswordErrorMsg.textContent =
      "Password (8 - 16) must contain 1 uppercase, 1 lowercase, 1 digit, 1 special character.";
    lgPasswordErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!isValid) {
    e.preventDefault();
  }
});

// Login input event

loginEmailInput.addEventListener("input", () => {
  const email = loginEmailInput.value.trim();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (emailRegex.test(email)) {
    lgEmailErrorMsg.textContent = "";
    lgEmailErrorMsg.classList.add("d-none");
  } else {
    lgEmailErrorMsg.textContent = "Enter a valid email";
    lgEmailErrorMsg.classList.remove("d-none");
  }
});

loginPassword.addEventListener("input", () => {
  const password = loginPassword.value.trim();

  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  if (passRegex.test(password)) {
    lgPasswordErrorMsg.textContent = "";
    lgPasswordErrorMsg.classList.add("d-none");
  }
});

// -----------------------------------------------------------

// Register Submit event

const regForm = document.querySelector("#register-form");
const nameInput = document.querySelector("#name-input");
const ageInput = document.querySelector("#age-input");
const regEmailInput = document.querySelector("#reg-email-input");
const regPasswordInput = document.querySelector("#reg-password-input");
const confirmPassInput = document.querySelector("#confirm-password-input");

const nameErrorMsg = document.querySelector("#name-error");
const ageErrorMsg = document.querySelector("#age-error");
const regEmailErrorMsg = document.querySelector("#reg-email-error");
const regPasswordErrorMsg = document.querySelector("#reg-password-error");
const confirmPasswordErrorMsg = document.querySelector(
  "#confirm-password-error"
);

regForm.addEventListener("submit", (e) => {
  // Clear all previous errors first
  nameErrorMsg.classList.add("d-none");
  ageErrorMsg.classList.add("d-none");
  regEmailErrorMsg.classList.add("d-none");
  regPasswordErrorMsg.classList.add("d-none");
  confirmPasswordErrorMsg.classList.add("d-none");

  const name = nameInput.value.trim();
  const age = ageInput.value.trim();
  const email = regEmailInput.value.trim();
  const password = regPasswordInput.value.trim();
  const confirmPassword = confirmPassInput.value.trim();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  let isValid = true; // Track validation status

  if (name.length < 2) {
    nameErrorMsg.textContent = "Enter your name";
    nameErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (age < 9) {
    ageErrorMsg.textContent = "You must be older than 9";
    ageErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!emailRegex.test(email)) {
    regEmailErrorMsg.textContent = "Enter a valid email";
    regEmailErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!passRegex.test(password)) {
    regPasswordErrorMsg.textContent = "Enter strong password";
    regPasswordErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (password !== confirmPassword) {
    // Fixed comparison
    confirmPasswordErrorMsg.textContent = "Passwords don't match";
    confirmPasswordErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  // Only prevent submission if validation fails
  if (!isValid) {
    e.preventDefault();
  }
  // If isValid is true, form submits normally
});
// ---------------------------------------------------------

// Register input event

nameInput.addEventListener("input", () => {
  const name = nameInput.value.trim();

  if (name.length > 1) {
    nameErrorMsg.textContent = "";
    nameErrorMsg.classList.add("d-none");
  } else {
    nameErrorMsg.textContent = "Enter your name";
    nameErrorMsg.classList.remove("d-none");
  }
});

ageInput.addEventListener("input", () => {
  const age = ageInput.value.trim();

  if (age > 8) {
    ageErrorMsg.textContent = "";
    ageErrorMsg.classList.add("d-none");
  } else {
    ageErrorMsg.textContent = "You must be older than 9";
    ageErrorMsg.classList.remove("d-none");
  }
});

regEmailInput.addEventListener("input", () => {
  const email = regEmailInput.value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (emailRegex.test(email)) {
    regEmailErrorMsg.textContent = "";
    regEmailErrorMsg.classList.add("d-none");
  } else {
    regEmailErrorMsg.textContent = "Enter a valid email";
    regEmailErrorMsg.classList.remove("d-none");
  }
});

regPasswordInput.addEventListener("input", () => {
  const password = regPasswordInput.value.trim();
  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  if (!passRegex.test(password)) {
    regPasswordErrorMsg.textContent = "Enter strong password";
    regPasswordErrorMsg.classList.remove("d-none");
  } else {
    regPasswordErrorMsg.textContent = "";
    regPasswordErrorMsg.classList.add("d-none");
  }
});

confirmPassInput.addEventListener("input", () => {
  const password = regPasswordInput.value.trim();
  const confirmPassword = confirmPassInput.value.trim();

  if (!confirmPassword.includes(password)) {
    confirmPasswordErrorMsg.textContent = "Password does'nt match";
    confirmPasswordErrorMsg.classList.remove("d-none");
  } else {
    confirmPasswordErrorMsg.textContent = "";
    confirmPasswordErrorMsg.classList.add("d-none");
  }
});
