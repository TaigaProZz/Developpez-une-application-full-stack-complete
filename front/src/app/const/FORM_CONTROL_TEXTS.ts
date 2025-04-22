export const formControlTextsConstants = {
  // email
  EMAIL_REQUIRED: 'L\'adresse e-mail est requise.',
  EMAIL_INVALID: 'L\'adresse e-mail est invalide.',

  // username
  USERNAME_LENGTH_MIN: 'Le nom d\'utilisateur doit comporter au moins 3 caractères.',
  USERNAME_REQUIRED: 'Le nom d\'utilisateur est requis.',

  // password
  PASSWORD_REQUIRED: 'Le mot de passe est requis.',
  PASSWORD_LENGTH_MIN: 'Le mot de passe doit comporter au moins 8 caractères.',
  PASSWORD_PATTERN: 'Le mot de passe doit contenir au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.'
} as const;
