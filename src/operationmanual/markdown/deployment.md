## Deployment auf hireme-build oder hireme-prod

Um ein gebautes Artifakt zu deployen, muss dieses auf einem Servlet Container 3+ deployed werden. Bei Jetty geschiet dies, indem man bei aktivierten Auto-Deploy das WAR in ${JETTY_HOME}/webapps` kopiert. 