1. Simple login page: User role: Admin, Reader (implement refresh token flow)
2. Home page:
Project structure
a. Display the project structure from github to site
b. Display the raw content of each file when clicking on it
Sample data
a. Can filter the data in sample folder by subscription, resource group, cluster (for reader, based on github data; for admin, have a toggle to switch from github data to db and vice versa)
b. Display the data as table
3. Admin page:
Sync data
a. Button to sync data from github to db
b. Button to sync data from db to github
Modify data in db
a. Create/Edit: Input name of subscription (can add new), name of resource group (can add new), name of cluster (can add new) and the file of data to import (support json and csv)
b. Delete items: If input only subscription, delete all related to that subscription. If input subscription and resource group, delete all related to that resource group. If input subscription and resource group and cluster, delete all related to the cluster
4. Cron job to sync data from db to github at a specific time of day
5. Webhook to modify data in db when there are changes to sample folder on github

New small requirements can be added along the way (that is what clients do every day tbh), so keep your code as flexible and clean as possible

Keywords:
Github API, workflow yaml

Notes:
make PR, review, merge
workflow to deploy site
daily report (did, doing, will do)
manage tasks on github project (like jira later)