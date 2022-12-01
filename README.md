# The task
Create an App which can be used to get the meanings for acronyms.

## Functional
1. User can enter an acronym or initialism.
2. User is then presented with a list of corresponding meanings.

## Requirements
* Use the following API http://www.nactem.ac.uk/software/acromine/rest.html
* Project must be written in Native Android and Kotlin
* Use MVVM architecture and Data binding
* You can use any third-party component for networking module
* Basic testcases should be added
* Handle all the possible error cases
* Project must be posted to Github

# System Design

## Functional requirements
- [ ] Users should be able to enter text
- [ ] Users should be able to see data download progress
- [ ] Users should be able to scroll through the list of results
- [ ] Users should be able to see errors properly

## Non-functional requirements
- [ ] Results caching
- [ ] Optimal real-time response to text input

## Out of scope
Nothing

## High-level Diagram
![High-level diagram](/media/system_design_high_level.svg)

## Search for acronyms Flow
![Search for acronyms diagram](/media/system_design_search_acronyms.svg)
