name: Bug Report
description: File a bug report
title: "[Bug]: "
labels: ["bug", "triage me"]
body:
  - type: markdown
    attributes:
      value: |
        Thanks for taking the time to fill out this bug report!
  - type: dropdown
    id: sample-app
    attributes:
      label: Sample app
      description: What sample app did you encounter a bug on?
      options:
        - Crane
        - JetNews
        - Jetcaster
        - Jetchat
        - Jetsnack
        - Jetsurvey
        - Owl
        - Rally
        - Other (bug not related to sample app)
    validations:
      required: true
  - type: textarea
    id: what-happened
    attributes:
      label: What happened?
      description: Also tell us, what did you expect to happen?
      placeholder: Tell us what you see!
      value: "A bug happened!"
    validations:
      required: true
  - type: textarea
    id: logs
    attributes:
      label: Relevant logcat output
      description: Please copy and paste any relevant logcat output. This will be automatically formatted into code, so no need for backticks.
      render: shell
  - type: checkboxes
    id: terms
    attributes:
      label: Code of Conduct
      description: By submitting this issue, you agree to follow our [Code of Conduct](CODE_OF_CONDUCT.md)
      options:
        - label: I agree to follow this project's Code of Conduct
          required: true
