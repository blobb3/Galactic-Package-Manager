class ApiService {
    constructor(baseUrl = 'http://localhost:8080/api') {
        this.baseUrl = baseUrl;
    }

    async getAllPackages() {
        try {
            const response = await fetch(`${this.baseUrl}/packages`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching packages:", error);
            return [];
        }
    }

    async getPackageById(id) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching package ${id}:`, error);
            return null;
        }
    }

    async searchPackages(query) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/search?query=${encodeURIComponent(query)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error searching packages:", error);
            return [];
        }
    }

    async getPackagesByCompatibility(faction) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/compatibility/${encodeURIComponent(faction)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching packages for faction ${faction}:`, error);
            return [];
        }
    }
    async getPackageDetails(id) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/${id}/details`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching details for package ${id}:`, error);
            return null;
        }
    }

}